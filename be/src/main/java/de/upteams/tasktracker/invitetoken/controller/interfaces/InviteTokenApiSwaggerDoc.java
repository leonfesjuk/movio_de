package de.upteams.tasktracker.invitetoken.controller.interfaces;

import de.upteams.tasktracker.exception.handling.response.ErrorResponseDto;
import de.upteams.tasktracker.invitetoken.dto.request.GenerateInviteTokensRequest;
import de.upteams.tasktracker.invitetoken.dto.response.InviteTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(
        name = "Invite Token Management",
        description = "Operations for generating and managing invite tokens. Restricted to ADMIN role only."
)
@SecurityRequirement(name = "bearerAuth")
public interface InviteTokenApiSwaggerDoc {

    @Operation(
            summary = "Generate invite tokens",
            description = "Generates the specified number of unique invite tokens. " +
                    "Each token can be used exactly once to complete the registration process. " +
                    "Raw token values are returned only in this response and cannot be retrieved later."
    )
    @RequestBody(
            description = "Number of tokens to generate (1–100)",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenerateInviteTokensRequest.class)
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Tokens generated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = InviteTokenResponse.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error — count is out of allowed range (1–100)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied — ADMIN role required",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    ResponseEntity<List<InviteTokenResponse>> generateTokens(GenerateInviteTokensRequest request);

    // ------------------------------------------------------------------ //

    @Operation(
            summary = "Get all invite tokens",
            description = "Returns a list of all invite tokens with their status. " +
                    "Raw token values are not included — only metadata (id, createdAt, usedAt, used)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Token list retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = InviteTokenResponse.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied — ADMIN role required",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    ResponseEntity<List<InviteTokenResponse>> getAllTokens();
}
