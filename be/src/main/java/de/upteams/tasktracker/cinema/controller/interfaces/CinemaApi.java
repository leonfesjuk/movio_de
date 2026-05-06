package de.upteams.tasktracker.cinema.controller.interfaces;

import de.upteams.tasktracker.cinema.dto.request.CinemaCreateDto;
import de.upteams.tasktracker.cinema.dto.request.CinemaUpdateDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaListDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import de.upteams.tasktracker.security.service.AuthUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Cinemas", description = "Cinemas API")
@RequestMapping("/api/v1/cinemas")
public interface CinemaApi {

    @Operation(summary = "Get all cinemas for organization")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cinemas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CinemaListDto.class)))
    })
    @GetMapping
    ResponseEntity<CinemaListDto> getAll(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Filter by city name") @RequestParam(required = false) String city,
            @AuthenticationPrincipal AuthUserDetails principal
    );

    @Operation(summary = "Get cinema by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cinema details"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Cinema not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<CinemaResponseDto> getById(
            @Parameter(description = "Cinema UUID") @PathVariable UUID id,
            @AuthenticationPrincipal AuthUserDetails principal
    );

    @Operation(summary = "Create a new cinema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cinema created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    ResponseEntity<CinemaResponseDto> create(
            @Valid @RequestBody CinemaCreateDto dto,
            @AuthenticationPrincipal AuthUserDetails principal
    );

    @Operation(summary = "Update an existing cinema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cinema updated"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Cinema not found")
    })
    @PutMapping("/{id}")
    ResponseEntity<CinemaResponseDto> update(
            @Parameter(description = "Cinema UUID") @PathVariable UUID id,
            @Valid @RequestBody CinemaUpdateDto dto,
            @AuthenticationPrincipal AuthUserDetails principal
    );

    @Operation(summary = "Delete a cinema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cinema deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "422", description = "Cannot delete cinema with events")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "Cinema UUID") @PathVariable UUID id,
            @AuthenticationPrincipal AuthUserDetails principal
    );
}
