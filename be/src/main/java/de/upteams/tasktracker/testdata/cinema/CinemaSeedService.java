package de.upteams.tasktracker.testdata.cinema;

import de.upteams.tasktracker.cinema.entity.Cinema;
import de.upteams.tasktracker.cinema.persistence.CinemaRepository;
import de.upteams.tasktracker.testdata.core.SeedCommand;
import de.upteams.tasktracker.testdata.core.SeedResult;
import de.upteams.tasktracker.testdata.core.TestDataSeeder;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CinemaSeedService implements TestDataSeeder {

    private final CinemaRepository cinemaRepository;
    private final UserRepository userRepository;
    private final TestCityGeonameDirectoryService cityGeonameDirectoryService;

    private static final String GEONAME_POOL_LIMIT_KEY = "geonamePoolLimit";
    private static final int DEFAULT_GEONAME_POOL_LIMIT = 500;

    private static final String[] CINEMA_PREFIXES = {
            "Nova", "Prime", "Grand", "Sky", "Star", "City", "Galaxy", "Aurora"
    };

    @Override
    public String name() {
        return "cinema";
    }

    @Override
    @Transactional
    public SeedResult seed(SeedCommand command) {
        Map<String, String> options = command.options() == null ? Map.of() : command.options();
        int geonamePoolLimit = parseGeonamePoolLimit(options);

        if (command.skipIfNotEmpty() && cinemaRepository.count() > 0) {
            return new SeedResult(name(), "skipped", 0, cinemaRepository.count(), "table is not empty");
        }

        List<AppUser> testUsers = userRepository.findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().toLowerCase(Locale.ROOT).startsWith("test"))
                .sorted(Comparator.comparing(AppUser::getEmail))
                .limit(5)
                .toList();

        if (testUsers.isEmpty()) {
            return new SeedResult(name(), "skipped", 0, cinemaRepository.count(), "no test users found");
        }

        List<Long> geonameIds = cityGeonameDirectoryService.getCityGeonameIds(geonamePoolLimit);

        if (geonameIds.isEmpty()) {
            return new SeedResult(name(), "skipped", 0, cinemaRepository.count(), "no active geonames found");
        }

        Random random = new Random();
        List<Cinema> cinemas = new ArrayList<>();
        int serial = 1;

        for (AppUser owner : testUsers) {
            int cinemasPerUser = 1 + random.nextInt(3); // 1..3
            for (int i = 0; i < cinemasPerUser; i++) {
                Long geonameId = geonameIds.get(random.nextInt(geonameIds.size()));
                String postfix = Integer.toString(serial++);

                Cinema cinema = new Cinema();
                cinema.setOrganizationId(owner.getId());
                cinema.setGeonameId(geonameId);
                cinema.setName(CINEMA_PREFIXES[random.nextInt(CINEMA_PREFIXES.length)] + " Cinema " + postfix);
                cinema.setAddress("Test street " + (100 + serial));
                cinema.setWebLink("https://cinema-test.example/c" + postfix);
                cinemas.add(cinema);
            }
        }

        cinemaRepository.saveAll(cinemas);
        return new SeedResult(name(), "ok", cinemas.size(), cinemaRepository.count(), "created (1-3 cinemas per user)");
    }

    private int parseGeonamePoolLimit(Map<String, String> options) {
        try {
            return Integer.parseInt(
                    options.getOrDefault(GEONAME_POOL_LIMIT_KEY, String.valueOf(DEFAULT_GEONAME_POOL_LIMIT))
            );
        } catch (Exception ignored) {
            return DEFAULT_GEONAME_POOL_LIMIT;
        }
    }
}
