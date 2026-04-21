package de.upteams.tasktracker.event.controller.interfaces;

import de.upteams.tasktracker.event.dto.request.EventCreateDto;
import de.upteams.tasktracker.event.dto.request.EventUpdateDto;
import de.upteams.tasktracker.event.dto.response.EventListDto;
import de.upteams.tasktracker.event.dto.response.EventResponseDto;
import de.upteams.tasktracker.security.service.AuthUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Events", description = "Events API")
@RequestMapping("/api/events")
public interface EventApi {

    @Operation(summary = "Get all events", description = "US-130, US-133, US-200, US-201")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventListDto.class)))
    })
    @GetMapping
    ResponseEntity<EventListDto> getAll(
            @Parameter(description = "Filter by cinema UUID")
            @RequestParam(required = false) UUID cinemaId,
            @Parameter(description = "Filter by city geonameId")
            @RequestParam(required = false) Long cityGeonameId,
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal AuthUserDetails principal
    );

    @Operation(summary = "Get event by ID", description = "US-130, US-200, US-201")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<EventResponseDto> getById(
            @Parameter(description = "Event UUID") @PathVariable UUID id
    );

    @Operation(summary = "Create event", description = "US-131")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Cinema not found")
    })
    @PostMapping
    ResponseEntity<EventResponseDto> create(
            @Parameter(description = "Event data") @RequestBody EventCreateDto dto,
            @AuthenticationPrincipal AuthUserDetails principal
    );

    @Operation(summary = "Update event", description = "US-131")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("/{id}")
    ResponseEntity<EventResponseDto> update(
            @Parameter(description = "Event UUID") @PathVariable UUID id,
            @Parameter(description = "Event data") @RequestBody EventUpdateDto dto,
            @AuthenticationPrincipal AuthUserDetails principal
    );

    @Operation(summary = "Delete event", description = "US-131")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "Event UUID") @PathVariable UUID id,
            @AuthenticationPrincipal AuthUserDetails principal
    );
}
