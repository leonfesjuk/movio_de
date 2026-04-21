package de.upteams.tasktracker.event.controller.impl;

import de.upteams.tasktracker.event.controller.interfaces.EventApi;
import de.upteams.tasktracker.event.dto.request.EventCreateDto;
import de.upteams.tasktracker.event.dto.request.EventUpdateDto;
import de.upteams.tasktracker.event.dto.response.EventListDto;
import de.upteams.tasktracker.event.dto.response.EventResponseDto;
import de.upteams.tasktracker.event.service.interfaces.EventService;
import de.upteams.tasktracker.security.service.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventApi {

    private final EventService eventService;

    @Override
    public ResponseEntity<EventListDto> getAll(UUID cinemaId, Long cityGeonameId, int page, int size, AuthUserDetails principal) {
        UUID organizationId = principal != null ? principal.user().getId() : null;
        EventListDto result = eventService.getAll(cinemaId, cityGeonameId, page, size, organizationId);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<EventResponseDto> getById(UUID id) {
        EventResponseDto result = eventService.getById(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<EventResponseDto> create(EventCreateDto dto, AuthUserDetails principal) {
        UUID organizationId = principal.user().getId();
        EventResponseDto result = eventService.create(dto, organizationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<EventResponseDto> update(UUID id, EventUpdateDto dto, AuthUserDetails principal) {
        UUID organizationId = principal.user().getId();
        EventResponseDto result = eventService.update(id, dto, organizationId);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> delete(UUID id, AuthUserDetails principal) {
        UUID organizationId = principal.user().getId();
        eventService.delete(id, organizationId);
        return ResponseEntity.noContent().build();
    }
}
