package de.upteams.tasktracker.testdata.core;

import de.upteams.tasktracker.cinema.entity.Cinema;
import de.upteams.tasktracker.cinema.persistence.CinemaRepository;
import de.upteams.tasktracker.event.entity.Event;
import de.upteams.tasktracker.event.persistence.EventRepository;
import de.upteams.tasktracker.testdata.core.dto.SeedAllPresetRequest;
import de.upteams.tasktracker.testdata.core.dto.SeedAllResponse;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.persistence.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Profile("dev")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "testdata.api", name = "enabled", havingValue = "true")
@RequestMapping("/internal/test-data")
@Tag(
        name = "Test Data (Dev)",
        description = "Development-only endpoints for generating test data. " +
                "Available only with active 'dev' profile and testdata.api.enabled=true."
)
public class TestDataController {

    private final List<TestDataSeeder> seeders;
    private final UserRepository userRepository;
    private final CinemaRepository cinemaRepository;
    private final EventRepository eventRepository;
    private final SeedDefaultsFactory seedDefaultsFactory;

    @Operation(
            summary = "Generate test data for all seeders",
            description = "Runs all registered test-data seeders by centralized preset."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Test data generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping("/seed/all")
    public ResponseEntity<SeedAllResponse> seedAll(
            @Parameter(description = "Preset-only request body. Example: {\"preset\":\"mvp\"}")
            @RequestBody(required = false) SeedAllPresetRequest request
    ) {
        String preset = seedDefaultsFactory.resolvePreset(request == null ? null : request.preset());

        List<SeedResult> rawResults = seeders.stream()
                .sorted(seederExecutionOrder())
                .map(seeder -> seeder.seed(seedDefaultsFactory.toAllSeederCommand(seeder.name(), preset)))
                .toList();

        String summary = buildSummary(rawResults, preset);

        List<SeedResult> resultsWithSummary = rawResults.stream()
                .map(r -> new SeedResult(
                        r.key(),
                        r.status(),
                        r.created(),
                        r.totalInDb(),
                        r.message() + " | " + summary
                ))
                .toList();

        SeedAllResponse.GeneratedStructure structure = buildStructure();

        return ResponseEntity.ok(new SeedAllResponse(resultsWithSummary, summary, structure));
    }

    private SeedAllResponse.GeneratedStructure buildStructure() {
        List<AppUser> users = userRepository.findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().toLowerCase(Locale.ROOT).startsWith("test"))
                .sorted(Comparator.comparing(AppUser::getEmail))
                .toList();

        Map<UUID, List<Cinema>> cinemasByOrg = cinemaRepository.findAll().stream()
                .collect(Collectors.groupingBy(Cinema::getOrganizationId));

        Map<UUID, List<Event>> eventsByCinemaId = eventRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> e.getCinema().getId()));

        List<SeedAllResponse.UserNode> userNodes = users.stream()
                .map(user -> {
                    List<SeedAllResponse.CinemaNode> cinemaNodes = cinemasByOrg
                            .getOrDefault(user.getId(), List.of())
                            .stream()
                            .sorted(Comparator.comparing(Cinema::getName))
                            .map(cinema -> {
                                List<SeedAllResponse.EventNode> eventNodes = eventsByCinemaId
                                        .getOrDefault(cinema.getId(), List.of())
                                        .stream()
                                        .sorted(Comparator.comparing(Event::getDatetime))
                                        .map(event -> new SeedAllResponse.EventNode(event.getId(), event.getTitle()))
                                        .toList();

                                return new SeedAllResponse.CinemaNode(
                                        cinema.getId(),
                                        cinema.getName(),
                                        eventNodes
                                );
                            })
                            .toList();

                    return new SeedAllResponse.UserNode(
                            user.getId(),
                            user.getEmail(),
                            cinemaNodes
                    );
                })
                .toList();

        return new SeedAllResponse.GeneratedStructure(userNodes);
    }

    private String buildSummary(List<SeedResult> results, String preset) {
        Map<String, Integer> createdByKey = results.stream()
                .collect(Collectors.toMap(
                        SeedResult::key,
                        SeedResult::created,
                        Integer::sum
                ));

        int users = createdByKey.getOrDefault("user", 0);
        int cinemas = createdByKey.getOrDefault("cinema", 0);
        int events = createdByKey.getOrDefault("event", 0);

        return "preset=" + preset + ", users=" + users + ", cinemas=" + cinemas + ", events=" + events;
    }

    private Comparator<TestDataSeeder> seederExecutionOrder() {
        Map<String, Integer> priority = Map.of(
                "invite-token", 10,
                "user", 20,
                "cinema", 30,
                "event", 40
        );

        return Comparator
                .comparingInt((TestDataSeeder s) -> priority.getOrDefault(s.name(), 1_000))
                .thenComparing(TestDataSeeder::name);
    }
}
