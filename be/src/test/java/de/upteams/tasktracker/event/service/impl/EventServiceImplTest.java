package de.upteams.tasktracker.event.service.impl;

import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import de.upteams.tasktracker.cinema.entity.Cinema;
import de.upteams.tasktracker.cinema.persistence.CinemaRepository;
import de.upteams.tasktracker.event.dto.response.EventResponseDto;
import de.upteams.tasktracker.event.entity.Event;
import de.upteams.tasktracker.event.entity.TimeFlag;
import de.upteams.tasktracker.event.exception.EventNotFoundException;
import de.upteams.tasktracker.event.persistence.EventRepository;
import de.upteams.tasktracker.event.persistence.TimeFlagRepository;
import de.upteams.tasktracker.event.service.interfaces.EventService;
import de.upteams.tasktracker.event.utils.EventMappingService;
import de.upteams.tasktracker.event.utils.TimeFlagMappingService;
import de.upteams.tasktracker.utils.BaseUuidEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private TimeFlagRepository timeFlagRepository;
    @Mock
    private CinemaRepository cinemaRepository;
    @Mock
    private EventMappingService eventMappingService;
    @Mock
    private TimeFlagMappingService timeFlagMappingService;

    @InjectMocks
    private EventServiceImpl eventService;

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

    @Test
    void getById_shouldReturnEventResponseDtoWhenFound() {
        UUID eventId = UUID.randomUUID();
        UUID cinemaId = UUID.randomUUID();

        Event mockEvent = new Event();
        setEntityId(mockEvent, eventId);
        mockEvent.setTitle("Test Event Title");
        mockEvent.setDescription("Test Event Description");
        mockEvent.setDatetime(LocalDateTime.now());

        Cinema mockCinema = new Cinema();
        setEntityId(mockCinema, cinemaId);
        mockCinema.setName("Test Cinema");
        mockCinema.setAddress("Test Address");
        mockCinema.setOrganizationId(UUID.randomUUID());

        mockEvent.setCinema(mockCinema);

        TimeFlag mockTimeFlag = new TimeFlag(eventId, true, false, true);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEvent));
        when(timeFlagRepository.findByEventId(eventId)).thenReturn(Optional.of(mockTimeFlag));

        EventResponseDto expectedDto = new EventResponseDto(
            eventId,
            mockEvent.getTitle(),
            mockEvent.getDescription(),
            mockEvent.getImageUrl(),
            mockEvent.getSeanceLink(),
            mockEvent.getDatetime(),
            new CinemaResponseDto(
                cinemaId,
                mockCinema.getName(),
                mockCinema.getAddress(),
                null
            ),
            new EventResponseDto.TimeFlagDto(
                mockTimeFlag.getTimeFlag1(),
                mockTimeFlag.getTimeFlag2(),
                mockTimeFlag.getTimeFlag3()
            )
        );

        EventResponseDto resultDto = eventService.getById(eventId);

        verify(eventRepository, times(1)).findById(eventId);
        verify(timeFlagRepository, times(1)).findByEventId(eventId);
        verifyNoInteractions(cinemaRepository, eventMappingService, timeFlagMappingService);

        assertEquals(expectedDto, resultDto);
    }

    @Test
    void getById_shouldThrowEventNotFoundExceptionWhenNotFound() {
        UUID eventId = UUID.randomUUID();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> {
            eventService.getById(eventId);
        });

        verify(eventRepository, times(1)).findById(eventId);
        verifyNoInteractions(timeFlagRepository, cinemaRepository, eventMappingService, timeFlagMappingService);
    }
}
