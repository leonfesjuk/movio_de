package de.upteams.tasktracker.testdata.core;

import de.upteams.tasktracker.testdata.core.dto.SeedRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Operation(
            summary = "Generate test data for all seeders",
            description = "Runs all registered test-data seeders with common input parameters."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Test data generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping("/seed/all")
    public ResponseEntity<List<SeedResult>> seedAll(
            @Parameter(description = "Common seeding parameters (optional). If omitted, defaults are used.")
            @Valid @RequestBody(required = false) SeedRequest request
    ) {
        SeedCommand command = toCommand(request);
        List<SeedResult> results = seeders.stream()
                .map(seeder -> seeder.seed(command))
                .toList();
        return ResponseEntity.ok(results);
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
