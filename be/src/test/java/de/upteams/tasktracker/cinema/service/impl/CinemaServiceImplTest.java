package de.upteams.tasktracker.cinema.service.impl;

import de.upteams.tasktracker.cinema.dto.request.CinemaCreateDto;
import de.upteams.tasktracker.cinema.dto.request.CinemaUpdateDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaListDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import de.upteams.tasktracker.cinema.entity.Cinema;
import de.upteams.tasktracker.cinema.exception.CinemaNotFoundException;
import de.upteams.tasktracker.cinema.persistence.CinemaRepository;
import de.upteams.tasktracker.cinema.utils.CinemaMappingService;
import de.upteams.tasktracker.event.persistence.EventRepository;
import de.upteams.tasktracker.exception.handling.exceptions.common.RestApiException;
import de.upteams.tasktracker.utils.BaseUuidEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaServiceImplTest {

    @Mock
    private CinemaRepository cinemaRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private CinemaMappingService cinemaMappingService;

    @InjectMocks
    private CinemaServiceImpl cinemaService;

    private void setEntityId(Object instance, UUID id) {
        try {
            Field idField = BaseUuidEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(instance, id);
        } catch (NoSuchFieldException e1) {
            try {
                Field idField = instance.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(instance, id);
            } catch (NoSuchFieldException e2) {
                throw new RuntimeException("Could not find 'id' field", e2);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("Could not access 'id' field", e2);
            }
        } catch (IllegalAccessException e1) {
            throw new RuntimeException("Could not access 'id' field", e1);
        }
    }

    @Test
    void getById_shouldReturnCinemaWhenFound() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        Cinema cinema = createMockCinema(cinemaId, organizationId);
        CinemaResponseDto expectedDto = new CinemaResponseDto(cinemaId, "Test Cinema", "Test Address", null);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(cinema));
        when(cinemaMappingService.mapEntityToDto(cinema)).thenReturn(expectedDto);

        CinemaResponseDto result = cinemaService.getById(cinemaId, organizationId);

        assertNotNull(result);
        assertEquals(expectedDto, result);
        verify(cinemaRepository).findById(cinemaId);
    }

    @Test
    void getById_shouldThrowCinemaNotFoundExceptionWhenNotFound() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.empty());

        assertThrows(CinemaNotFoundException.class, () -> cinemaService.getById(cinemaId, organizationId));
    }

    @Test
    void getById_shouldThrowForbiddenWhenOrganizationDoesNotOwnCinema() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        UUID otherOrganizationId = UUID.randomUUID();
        Cinema cinema = createMockCinema(cinemaId, otherOrganizationId);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(cinema));

        RestApiException ex = assertThrows(RestApiException.class, () -> cinemaService.getById(cinemaId, organizationId));
        assertEquals(HttpStatus.FORBIDDEN, ex.getHttpStatus());
    }

    @Test
    void getAll_shouldReturnPaginatedList() {
        UUID organizationId = UUID.randomUUID();
        int page = 0;
        int size = 20;
        Cinema cinema = createMockCinema(UUID.randomUUID(), organizationId);
        Page<Cinema> cinemaPage = new PageImpl<>(List.of(cinema), PageRequest.of(page, size), 1);
        CinemaResponseDto expectedDto = new CinemaResponseDto(cinema.getId(), "Test Cinema", "Test Address", null);

        when(cinemaRepository.findByOrganizationId(eq(organizationId), any(PageRequest.class))).thenReturn(cinemaPage);
        when(cinemaMappingService.mapEntityToDto(cinema)).thenReturn(expectedDto);

        CinemaListDto result = cinemaService.getAll(page, size, null, organizationId);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
    }

    @Test
    void getAll_shouldReturnEmptyList() {
        UUID organizationId = UUID.randomUUID();
        int page = 0;
        int size = 20;
        Page<Cinema> emptyPage = new PageImpl<>(List.of(), PageRequest.of(page, size), 0);

        when(cinemaRepository.findByOrganizationId(eq(organizationId), any(PageRequest.class))).thenReturn(emptyPage);

        CinemaListDto result = cinemaService.getAll(page, size, null, organizationId);

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
    }

    @Test
    void create_shouldCreateCinema() {
        UUID organizationId = UUID.randomUUID();
        UUID cinemaId = UUID.randomUUID();
        CinemaCreateDto dto = new CinemaCreateDto();
        dto.setName("New Cinema");
        dto.setAddress("New Address");
        dto.setWebLink("https://new-cinema.com");
        dto.setGeonameId(2950159L);

        Cinema cinema = createMockCinema(cinemaId, organizationId);
        cinema.setName(dto.getName());
        cinema.setAddress(dto.getAddress());
        cinema.setWebLink(dto.getWebLink());
        cinema.setGeonameId(dto.getGeonameId());

        CinemaResponseDto expectedDto = new CinemaResponseDto(cinemaId, dto.getName(), dto.getAddress(), null);

        when(cinemaMappingService.mapEntityToDto(any(Cinema.class))).thenReturn(expectedDto);
        when(cinemaRepository.save(any(Cinema.class))).thenAnswer(inv -> {
            Cinema c = inv.getArgument(0);
            setEntityId(c, cinemaId);
            return c;
        });

        CinemaResponseDto result = cinemaService.create(dto, organizationId);

        assertNotNull(result);
        verify(cinemaRepository).save(any(Cinema.class));
    }

    @Test
    void update_shouldUpdateCinema() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        CinemaUpdateDto dto = new CinemaUpdateDto();
        dto.setName("Updated Cinema");
        dto.setAddress("Updated Address");
        dto.setWebLink("https://updated-cinema.com");
        dto.setGeonameId(2950159L);

        Cinema existingCinema = createMockCinema(cinemaId, organizationId);
        CinemaResponseDto expectedDto = new CinemaResponseDto(cinemaId, dto.getName(), dto.getAddress(), null);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(existingCinema));
        when(cinemaRepository.save(any(Cinema.class))).thenReturn(existingCinema);
        when(cinemaMappingService.mapEntityToDto(existingCinema)).thenReturn(expectedDto);

        CinemaResponseDto result = cinemaService.update(cinemaId, dto, organizationId);

        assertNotNull(result);
        assertEquals("Updated Cinema", existingCinema.getName());
        verify(cinemaRepository).save(existingCinema);
    }

    @Test
    void update_shouldThrowCinemaNotFoundExceptionWhenNotFound() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        CinemaUpdateDto dto = new CinemaUpdateDto();

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.empty());

        assertThrows(CinemaNotFoundException.class, () -> cinemaService.update(cinemaId, dto, organizationId));
    }

    @Test
    void update_shouldThrowForbiddenWhenOrganizationDoesNotOwnCinema() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        UUID otherOrganizationId = UUID.randomUUID();
        CinemaUpdateDto dto = new CinemaUpdateDto();
        Cinema existingCinema = createMockCinema(cinemaId, otherOrganizationId);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(existingCinema));

        RestApiException ex = assertThrows(RestApiException.class, () -> cinemaService.update(cinemaId, dto, organizationId));
        assertEquals(HttpStatus.FORBIDDEN, ex.getHttpStatus());
    }

    @Test
    void delete_shouldDeleteCinema() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        Cinema cinema = createMockCinema(cinemaId, organizationId);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(cinema));
        when(eventRepository.existsByCinemaId(cinemaId)).thenReturn(false);

        cinemaService.delete(cinemaId, organizationId);

        verify(cinemaRepository).delete(cinema);
    }

    @Test
    void delete_shouldThrowCinemaNotFoundExceptionWhenNotFound() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.empty());

        assertThrows(CinemaNotFoundException.class, () -> cinemaService.delete(cinemaId, organizationId));
    }

    @Test
    void delete_shouldThrowUnprocessableEntityWhenCinemaHasEvents() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        Cinema cinema = createMockCinema(cinemaId, organizationId);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(cinema));
        when(eventRepository.existsByCinemaId(cinemaId)).thenReturn(true);

        RestApiException ex = assertThrows(RestApiException.class, () -> cinemaService.delete(cinemaId, organizationId));
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getHttpStatus());
    }

    @Test
    void delete_shouldThrowForbiddenWhenOrganizationDoesNotOwnCinema() {
        UUID cinemaId = UUID.randomUUID();
        UUID organizationId = UUID.randomUUID();
        UUID otherOrganizationId = UUID.randomUUID();
        Cinema cinema = createMockCinema(cinemaId, otherOrganizationId);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(cinema));

        RestApiException ex = assertThrows(RestApiException.class, () -> cinemaService.delete(cinemaId, organizationId));
        assertEquals(HttpStatus.FORBIDDEN, ex.getHttpStatus());
    }

    private Cinema createMockCinema(UUID cinemaId, UUID organizationId) {
        Cinema cinema = new Cinema();
        setEntityId(cinema, cinemaId);
        cinema.setName("Test Cinema");
        cinema.setAddress("Test Address");
        cinema.setWebLink("https://test-cinema.com");
        cinema.setOrganizationId(organizationId);
        cinema.setGeonameId(2950159L);
        return cinema;
    }
}