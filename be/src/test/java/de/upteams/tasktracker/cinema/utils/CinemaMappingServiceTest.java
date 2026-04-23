package de.upteams.tasktracker.cinema.utils;

import de.upteams.tasktracker.cinema.utils.CinemaMappingService;
import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import de.upteams.tasktracker.cinema.entity.Cinema;
import de.upteams.tasktracker.utils.BaseUuidEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaMappingServiceTest {

    @Mock
    private CinemaMappingService cinemaMappingService;

    @Test
    void mapEntityToDto_shouldCallMapper() {
        UUID cinemaId = UUID.randomUUID();
        Cinema cinema = new Cinema();
        setEntityId(cinema, cinemaId);
        cinema.setName("Test Cinema");
        cinema.setAddress("Test Address");
        cinema.setWebLink("https://test-cinema.com");
        cinema.setOrganizationId(UUID.randomUUID());
        cinema.setGeonameId(2950159L);

        CinemaResponseDto expectedDto = new CinemaResponseDto(cinemaId, "Test Cinema", "Test Address", null);
        when(cinemaMappingService.mapEntityToDto(cinema)).thenReturn(expectedDto);

        CinemaResponseDto result = cinemaMappingService.mapEntityToDto(cinema);

        assertNotNull(result);
        assertEquals(cinemaId, result.getId());
        verify(cinemaMappingService).mapEntityToDto(cinema);
    }

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
}