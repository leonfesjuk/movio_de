package de.upteams.tasktracker.testdata.event;

import de.upteams.tasktracker.testdata.core.SeedCommand;
import de.upteams.tasktracker.testdata.core.SeedDefaultsFactory;
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
@RequestMapping("/internal/test-data/events")
@Tag(
        name = "Test Data: Events (Dev)",
        description = "Development-only endpoint for generating event test data."
)
public class EventTestDataController {

    private final EventSeedService eventSeedService;
    private final SeedDefaultsFactory seedDefaultsFactory;

    @Operation(summary = "Generate event test data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event test data generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping("/seed")
    public ResponseEntity<SeedResult> seedEvents(
            @Parameter(description = "Seeding parameters (optional)")
            @Valid @RequestBody(required = false) SeedRequest request
    ) {
        return ResponseEntity.ok(eventSeedService.seed(
                seedDefaultsFactory.toSingleSeederCommand(request, 40, Map.of())
        ));
    }
}
