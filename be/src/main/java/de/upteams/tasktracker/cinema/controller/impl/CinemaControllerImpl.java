package de.upteams.tasktracker.cinema.controller.impl;

import de.upteams.tasktracker.cinema.controller.interfaces.CinemaApi;
import de.upteams.tasktracker.cinema.dto.request.CinemaCreateDto;
import de.upteams.tasktracker.cinema.dto.request.CinemaUpdateDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaListDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import de.upteams.tasktracker.cinema.service.interfaces.CinemaService;
import de.upteams.tasktracker.security.service.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CinemaControllerImpl implements CinemaApi {

    private final CinemaService cinemaService;

    @Override
    public ResponseEntity<CinemaListDto> getAll(int page, int size, String city, AuthUserDetails principal) {
        UUID organizationId = principal.user().getId();
        CinemaListDto result = cinemaService.getAll(page, size, city, organizationId);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<CinemaResponseDto> getById(UUID id, AuthUserDetails principal) {
        UUID organizationId = principal.user().getId();
        CinemaResponseDto result = cinemaService.getById(id, organizationId);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<CinemaResponseDto> create(CinemaCreateDto dto, AuthUserDetails principal) {
        UUID organizationId = principal.user().getId();
        CinemaResponseDto result = cinemaService.create(dto, organizationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<CinemaResponseDto> update(UUID id, CinemaUpdateDto dto, AuthUserDetails principal) {
        UUID organizationId = principal.user().getId();
        CinemaResponseDto result = cinemaService.update(id, dto, organizationId);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Void> delete(UUID id, AuthUserDetails principal) {
        UUID organizationId = principal.user().getId();
        cinemaService.delete(id, organizationId);
        return ResponseEntity.noContent().build();
    }
}
