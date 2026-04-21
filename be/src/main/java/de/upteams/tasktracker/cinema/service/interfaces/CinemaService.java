package de.upteams.tasktracker.cinema.service.interfaces;

import de.upteams.tasktracker.cinema.dto.request.CinemaCreateDto;
import de.upteams.tasktracker.cinema.dto.request.CinemaUpdateDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaListDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;

import java.util.UUID;

public interface CinemaService {
    CinemaResponseDto getById(UUID id, UUID organizationId);
    CinemaListDto getAll(int page, int size, String city, UUID organizationId);
    CinemaResponseDto create(CinemaCreateDto dto, UUID organizationId);
    CinemaResponseDto update(UUID id, CinemaUpdateDto dto, UUID organizationId);
    void delete(UUID id, UUID organizationId);
}
