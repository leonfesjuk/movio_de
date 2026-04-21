package de.upteams.tasktracker.event.utils;

import de.upteams.tasktracker.event.dto.request.EventCreateDto;
import de.upteams.tasktracker.event.dto.request.EventUpdateDto;
import de.upteams.tasktracker.event.dto.response.EventResponseDto;
import de.upteams.tasktracker.event.entity.Event;
import de.upteams.tasktracker.utils.BaseUuidEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EventMappingServiceTest {

    @Autowired
    private EventMappingService eventMappingService;

    @Test
    void mapCreateDtoToEntity_shouldMapFieldsCorrectly() {
        UUID testCinemaId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        EventCreateDto.TimeFlagDto dummyTimeFlag = new EventCreateDto.TimeFlagDto(true, false, true);

        EventCreateDto createDto = new EventCreateDto(
                "New Event Title",
                "New Event Description",
                "Location A",
                "2023-10-27T10:00:00",
                now,
                testCinemaId,
                dummyTimeFlag
        );

        Event event = eventMappingService.mapDtoToEntity(createDto);

        assertNotNull(event);
        assertEquals("New Event Title", event.getTitle());
        assertEquals("New Event Description", event.getDescription());

        assertNull(event.getId());
        assertNull(event.getCinema());
    }

    @Test
    void mapUpdateDtoToEntity_shouldMapFieldsCorrectly() {
        UUID testCinemaId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        EventUpdateDto.TimeFlagDto dummyTimeFlag = new EventUpdateDto.TimeFlagDto(false, true, false);

        EventUpdateDto updateDto = new EventUpdateDto(
                "Updated Event Title",
                "Updated event details.",
                "Location B",
                "2023-10-28T12:00:00",
                now,
                testCinemaId,
                dummyTimeFlag
        );

        Event event = eventMappingService.mapDtoToEntity(updateDto);

        assertNotNull(event);
        assertEquals("Updated Event Title", event.getTitle());
        assertEquals("Updated event details.", event.getDescription());

        assertNull(event.getId());
        assertNull(event.getCinema());
    }

    @Test
    void mapEntityToDto_shouldMapFieldsCorrectly() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();

        setEntityId(event, eventId);

        event.setTitle("Existing Event");
        event.setDescription("Description of the existing event.");

        EventResponseDto responseDto = eventMappingService.mapEntityToDto(event);

        assertNotNull(responseDto);
        assertEquals(eventId, responseDto.getId());
        assertEquals("Existing Event", responseDto.getTitle());
        assertEquals("Description of the existing event.", responseDto.getDescription());
        assertNull(responseDto.getCinema());
        assertNull(responseDto.getTimeFlags());
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
                throw new RuntimeException("Could not find 'id' field via reflection in Event or its superclass", e2);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("Could not access 'id' field via reflection", e2);
            }
        } catch (IllegalAccessException e1) {
            throw new RuntimeException("Could not access 'id' field via reflection", e1);
        }
    }
}
