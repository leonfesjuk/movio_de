package de.upteams.tasktracker.testdata.event;

import de.upteams.tasktracker.cinema.entity.Cinema;
import de.upteams.tasktracker.cinema.persistence.CinemaRepository;
import de.upteams.tasktracker.event.entity.Event;
import de.upteams.tasktracker.event.entity.TimeFlag;
import de.upteams.tasktracker.event.persistence.EventRepository;
import de.upteams.tasktracker.event.persistence.TimeFlagRepository;
import de.upteams.tasktracker.testdata.core.SeedCommand;
import de.upteams.tasktracker.testdata.core.SeedResult;
import de.upteams.tasktracker.testdata.core.TestDataSeeder;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventSeedService implements TestDataSeeder {

    private final EventRepository eventRepository;
    private final TimeFlagRepository timeFlagRepository;
    private final CinemaRepository cinemaRepository;
    private final UserRepository userRepository;

    private static final String[] FUTURE_TITLES = {
            "Future Premiere", "Upcoming Blockbuster", "Weekend Special", "Late Night Future"
    };

    private static final String[] PAST_TITLES = {
            "Past Screening", "Archive Session", "Retro Night", "Classic Replay"
    };

    @Override
    public String name() {
        return "event";
    }

    @Override
    @Transactional
    public SeedResult seed(SeedCommand command) {
        if (command.skipIfNotEmpty() && (eventRepository.count() > 0 || timeFlagRepository.count() > 0)) {
            return new SeedResult(name(), "skipped", 0, eventRepository.count(), "events/time_flags tables are not empty");
        }

        Set<UUID> testUserIds = userRepository.findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().toLowerCase(Locale.ROOT).startsWith("test"))
                .map(AppUser::getId)
                .collect(Collectors.toSet());

        if (testUserIds.isEmpty()) {
            return new SeedResult(name(), "skipped", 0, eventRepository.count(), "no test users found");
        }

        List<Cinema> testUserCinemas = cinemaRepository.findAll().stream()
                .filter(c -> testUserIds.contains(c.getOrganizationId()))
                .toList();

        if (testUserCinemas.isEmpty()) {
            return new SeedResult(name(), "skipped", 0, eventRepository.count(), "no cinemas for test users found");
        }

        List<EventMovieTemplates.MovieSeedTemplate> templates = EventMovieTemplates.MOVIES;
        if (templates.isEmpty()) {
            return new SeedResult(name(), "skipped", 0, eventRepository.count(), "movie templates are empty");
        }

        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        List<Event> events = new ArrayList<>();

        for (Cinema cinema : testUserCinemas) {
            int perCinema = 10 + random.nextInt(11); // 10..20
            int futureCount = Math.max(1, perCinema / 2);
            int pastCount = perCinema - futureCount;

            for (int i = 0; i < futureCount; i++) {
                EventMovieTemplates.MovieSeedTemplate template = templates.get(random.nextInt(templates.size()));
                String postfix = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

                Event event = new Event();
                event.setCinema(cinema);
                event.setTitle("[FUTURE] " + template.title() + " #" + postfix);
                event.setDescription("[future] " + template.descriptionText());
                event.setImageUrl(template.imageUrl());
                event.setSeanceLink(template.wikipediaLink());
                event.setDatetime(now.plusDays(1 + random.nextInt(45)).withHour(10 + random.nextInt(12)).withMinute(0));

                events.add(event);
            }

            for (int i = 0; i < pastCount; i++) {
                EventMovieTemplates.MovieSeedTemplate template = templates.get(random.nextInt(templates.size()));
                String postfix = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

                Event event = new Event();
                event.setCinema(cinema);
                event.setTitle("[PAST] " + template.title() + " #" + postfix);
                event.setDescription("[past] " + template.descriptionText());
                event.setImageUrl(template.imageUrl());
                event.setSeanceLink(template.wikipediaLink());
                event.setDatetime(now.minusDays(1 + random.nextInt(45)).withHour(10 + random.nextInt(12)).withMinute(0));

                events.add(event);
            }
        }

        List<Event> savedEvents = eventRepository.saveAll(events);

        List<TimeFlag> flags = new ArrayList<>(savedEvents.size());
        for (int i = 0; i < savedEvents.size(); i++) {
            Event saved = savedEvents.get(i);
            flags.add(new TimeFlag(
                    saved.getId(),
                    i % 2 == 0,
                    i % 3 == 0,
                    i % 5 == 0
            ));
        }
        timeFlagRepository.saveAll(flags);

        return new SeedResult(
                name(),
                "ok",
                savedEvents.size(),
                eventRepository.count(),
                "created (10-20 events per cinema, past+future) from EventMovieTemplates with linked time_flags: " + flags.size()
        );
    }
}