package de.upteams.tasktracker.testdata.invitetoken;

import de.upteams.tasktracker.testdata.core.SeedCommand;
import de.upteams.tasktracker.testdata.core.SeedResult;
import de.upteams.tasktracker.testdata.core.dto.SeedRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/internal/test-data/invite-tokens")
@Tag(
        name = "Test Data: Invite Tokens (Dev)",
        description = "Development-only endpoint for generating invite token test data. " +
                "Available only with active 'dev' profile and testdata.api.enabled=true."
)
public class InviteTokenTestDataController {

    private final InviteTokenSeedService inviteTokenSeedService;

    @Operation(
            summary = "Generate invite-token test data",
            description = "Generates invite tokens for development/testing. " +
                    "By default creates 20 records and marks part of them as used."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Invite-token test data generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping("/seed")
    public ResponseEntity<SeedResult> seedInviteTokens(
            @Parameter(description = "Seeding parameters (optional). If omitted, defaults are used.")
            @Valid @RequestBody(required = false) SeedRequest request
    ) {
        SeedCommand command = toCommand(request);
        return ResponseEntity.ok(inviteTokenSeedService.seed(command));
    }

    private SeedCommand toCommand(SeedRequest request) {
        if (request == null) {
            return new SeedCommand(20, false, Map.of("markSomeAsUsed", "true"));
        }
        return new SeedCommand(
                request.count() == null ? 20 : request.count(),
                request.skipIfNotEmpty() != null && request.skipIfNotEmpty(),
                request.options() == null ? Map.of("markSomeAsUsed", "true") : request.options()
        );
    }
}
