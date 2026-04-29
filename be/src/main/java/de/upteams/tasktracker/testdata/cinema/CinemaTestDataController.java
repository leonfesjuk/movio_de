package de.upteams.tasktracker.testdata.cinema;

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
@RequestMapping("/internal/test-data/cinemas")
@Tag(
        name = "Test Data: Cinemas (Dev)",
        description = "Development-only endpoint for generating cinema test data."
)
public class CinemaTestDataController {

    private final CinemaSeedService cinemaSeedService;

    @Operation(summary = "Generate cinema test data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cinema test data generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping("/seed")
    public ResponseEntity<SeedResult> seedCinemas(
            @Parameter(description = "Seeding parameters (optional)")
            @Valid @RequestBody(required = false) SeedRequest request
    ) {
        SeedCommand command = toCommand(request);
        return ResponseEntity.ok(cinemaSeedService.seed(command));
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
