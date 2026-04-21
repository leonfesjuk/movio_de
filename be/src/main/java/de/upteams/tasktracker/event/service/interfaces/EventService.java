package de.upteams.tasktracker.event.service.interfaces;

import de.upteams.tasktracker.event.dto.request.EventCreateDto;
import de.upteams.tasktracker.event.dto.request.EventUpdateDto;
import de.upteams.tasktracker.event.dto.response.EventListDto;
import de.upteams.tasktracker.event.dto.response.EventResponseDto;

import java.util.UUID;

public interface EventService {

    EventResponseDto getById(UUID id);

    EventListDto getAll(UUID cinemaId, Long cityId, int page, int size, UUID organizationId);

    EventResponseDto create(EventCreateDto dto, UUID organizationId);

    EventResponseDto update(UUID id, EventUpdateDto dto, UUID organizationId);

    void delete(UUID id, UUID organizationId);
}
