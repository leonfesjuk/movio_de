package de.upteams.tasktracker.cinema.controller.impl;

import de.upteams.tasktracker.cinema.dto.request.CinemaCreateDto;
import de.upteams.tasktracker.cinema.dto.request.CinemaUpdateDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaListDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import de.upteams.tasktracker.cinema.service.interfaces.CinemaService;
import de.upteams.tasktracker.security.service.AuthUserDetails;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.entity.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaControllerImplTest {

    @Mock
    private CinemaService cinemaService;

    @InjectMocks
    private CinemaControllerImpl controller;

    @Test
    void getAll_shouldReturnOk() {
        int page = 0;
        int size = 20;
        UUID organizationId = UUID.randomUUID();
        AppUser user = createMockUser(organizationId);
        AuthUserDetails principal = new AuthUserDetails(user);
        CinemaListDto expectedDto = new CinemaListDto(List.of(), new CinemaListDto.PaginationDto(false));

        when(cinemaService.getAll(page, size, null, organizationId)).thenReturn(expectedDto);

        ResponseEntity<CinemaListDto> result = controller.getAll(page, size, null, principal);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(cinemaService).getAll(page, size, null, organizationId);
    }

    @Test
    void getById_shouldReturnOk() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        AppUser user = createMockUser(organizationId);
        AuthUserDetails principal = new AuthUserDetails(user);
        CinemaResponseDto expectedDto = new CinemaResponseDto(cinemaId, "Test Cinema", "Test Address", null);

        when(cinemaService.getById(cinemaId, organizationId)).thenReturn(expectedDto);

        ResponseEntity<CinemaResponseDto> result = controller.getById(cinemaId, principal);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedDto, result.getBody());
    }

    @Test
    void create_shouldReturnCreated() {
        UUID organizationId = UUID.randomUUID();
        AppUser user = createMockUser(organizationId);
        AuthUserDetails principal = new AuthUserDetails(user);
        CinemaCreateDto dto = new CinemaCreateDto();
        CinemaResponseDto expectedDto = new CinemaResponseDto(UUID.randomUUID(), "New Cinema", "Address", null);

        when(cinemaService.create(dto, organizationId)).thenReturn(expectedDto);

        ResponseEntity<CinemaResponseDto> result = controller.create(dto, principal);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedDto, result.getBody());
    }

    @Test
    void update_shouldReturnOk() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        AppUser user = createMockUser(organizationId);
        AuthUserDetails principal = new AuthUserDetails(user);
        CinemaUpdateDto dto = new CinemaUpdateDto();
        CinemaResponseDto expectedDto = new CinemaResponseDto(cinemaId, "Updated Cinema", "Address", null);

        when(cinemaService.update(cinemaId, dto, organizationId)).thenReturn(expectedDto);

        ResponseEntity<CinemaResponseDto> result = controller.update(cinemaId, dto, principal);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedDto, result.getBody());
    }

    @Test
    void delete_shouldReturnNoContent() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        AppUser user = createMockUser(organizationId);
        AuthUserDetails principal = new AuthUserDetails(user);

        doNothing().when(cinemaService).delete(cinemaId, organizationId);

        ResponseEntity<Void> result = controller.delete(cinemaId, principal);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(cinemaService).delete(cinemaId, organizationId);
    }

    private AppUser createMockUser(UUID organizationId) {
        AppUser user = new AppUser();
        try {
            java.lang.reflect.Field idField = user.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(user, organizationId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setName("testuser");
        user.setWebLink("https://test.com");
        user.setRole(Role.ROLE_USER);
        return user;
    }
}