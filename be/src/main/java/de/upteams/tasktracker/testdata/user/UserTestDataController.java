package de.upteams.tasktracker.testdata.user;

import de.upteams.tasktracker.testdata.core.SeedCommand;
import de.upteams.tasktracker.testdata.core.SeedResult;
import de.upteams.tasktracker.testdata.core.dto.SeedRequest;
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

import java.util.Map;

@RestController
@Profile("dev")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "testdata.api", name = "enabled", havingValue = "true")
@RequestMapping("/internal/test-data/users")
@Tag(
        name = "Test Data: Users (Dev)",
        description = "Development-only endpoint for generating user test data. " +
                "Available only with active 'dev' profile and testdata.api.enabled=true."
)
public class UserTestDataController {

    private final UserSeedService userSeedService;

    @Operation(
            summary = "Generate user test data",
            description = "Generates test users for development/testing. " +
                    "By default creates 20 records."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User test data generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping("/seed")
    public ResponseEntity<SeedResult> seedUsers(
            @Parameter(description = "Seeding parameters (optional). If omitted, defaults are used.")
            @Valid @RequestBody(required = false) SeedRequest request
    ) {
        SeedCommand command = toCommand(request);
        return ResponseEntity.ok(userSeedService.seed(command));
    }

    private SeedCommand toCommand(SeedRequest request) {
        if (request == null) {
            return new SeedCommand(20, false, Map.of());
        }
        return new SeedCommand(
                request.count() == null ? 20 : request.count(),
                request.skipIfNotEmpty() != null && request.skipIfNotEmpty(),
                request.options() == null ? Map.of() : request.options()
        );
    }
}
