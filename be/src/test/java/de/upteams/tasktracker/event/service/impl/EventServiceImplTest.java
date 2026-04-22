package de.upteams.tasktracker.event.service.impl;

import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import de.upteams.tasktracker.cinema.entity.Cinema;
import de.upteams.tasktracker.cinema.exception.CinemaNotFoundException;
import de.upteams.tasktracker.cinema.persistence.CinemaRepository;
import de.upteams.tasktracker.event.dto.request.EventCreateDto;
import de.upteams.tasktracker.event.dto.request.EventUpdateDto;
import de.upteams.tasktracker.event.dto.response.EventListDto;
import de.upteams.tasktracker.event.dto.response.EventResponseDto;
import de.upteams.tasktracker.event.entity.Event;
import de.upteams.tasktracker.event.entity.TimeFlag;
import de.upteams.tasktracker.event.exception.EventNotFoundException;
import de.upteams.tasktracker.event.persistence.EventRepository;
import de.upteams.tasktracker.event.persistence.TimeFlagRepository;
import de.upteams.tasktracker.event.service.interfaces.EventService;
import de.upteams.tasktracker.event.utils.EventMappingService;
import de.upteams.tasktracker.event.utils.TimeFlagMappingService;
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
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;

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

    @Test
    void getAll_shouldFilterByCinemaId() {
        UUID cinemaId = UUID.randomUUID();
        int page = 0;
        int size = 10;

        Event mockEvent = createMockEvent();
        Page<Event> eventPage = new PageImpl<>(List.of(mockEvent), PageRequest.of(page, size), 1);

        when(eventRepository.findUpcomingEventsByCinema(eq(cinemaId), any(Pageable.class))).thenReturn(eventPage);

        EventListDto result = eventService.getAll(cinemaId, null, page, size, null);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        verify(eventRepository).findUpcomingEventsByCinema(eq(cinemaId), any(Pageable.class));
    }

    @Test
    void getAll_shouldFilterByCityId() {
        Long cityId = 12345L;
        int page = 0;
        int size = 10;

        Event mockEvent = createMockEvent();
        Page<Event> eventPage = new PageImpl<>(List.of(mockEvent), PageRequest.of(page, size), 1);

        when(eventRepository.findUpcomingEventsByCity(eq(cityId), any(Pageable.class))).thenReturn(eventPage);

        EventListDto result = eventService.getAll(null, cityId, page, size, null);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        verify(eventRepository).findUpcomingEventsByCity(eq(cityId), any(Pageable.class));
    }

    @Test
    void getAll_shouldFilterByOrganizationId() {
        UUID orgId = UUID.randomUUID();
        int page = 0;
        int size = 10;

        Event mockEvent = createMockEvent();
        Page<Event> eventPage = new PageImpl<>(List.of(mockEvent), PageRequest.of(page, size), 1);

        when(eventRepository.findByOrganizationIdWithDateCheck(eq(orgId), any(Pageable.class))).thenReturn(eventPage);

        EventListDto result = eventService.getAll(null, null, page, size, orgId);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        verify(eventRepository).findByOrganizationIdWithDateCheck(eq(orgId), any(Pageable.class));
    }

    @Test
    void getAll_shouldReturnAllUpcomingEventsWhenNoFilters() {
        int page = 0;
        int size = 10;

        Event mockEvent = createMockEvent();
        Page<Event> eventPage = new PageImpl<>(List.of(mockEvent), PageRequest.of(page, size), 1);

        when(eventRepository.findUpcomingEvents(any(Pageable.class))).thenReturn(eventPage);

        EventListDto result = eventService.getAll(null, null, page, size, null);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertFalse(result.getPagination().getHasMore());
verify(eventRepository).findUpcomingEvents(any(Pageable.class));
    }

    @Test
    void create_shouldCreateEventWithTimeFlags() {
        UUID orgId = UUID.randomUUID();
        UUID cinemaId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        EventCreateDto dto = new EventCreateDto(
            "Title", "Description", "http://img.jpg", "http://seance", LocalDateTime.now().plusDays(1),
            cinemaId, new EventCreateDto.TimeFlagDto(true, false, true)
        );

        Cinema cinema = createMockCinema(cinemaId, orgId);
        Event event = createMockEvent();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());

        TimeFlag timeFlag = new TimeFlag(eventId, true, false, true);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(cinema));
        when(eventMappingService.mapDtoToEntity(dto)).thenReturn(event);
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> {
            Event e = inv.getArgument(0);
            setEntityId(e, eventId);
            return e;
        });
        when(timeFlagMappingService.mapCreateDtoToEntity(dto.getTimeFlags(), eventId)).thenReturn(timeFlag);
        when(timeFlagRepository.save(any(TimeFlag.class))).thenReturn(timeFlag);

        EventResponseDto result = eventService.create(dto, orgId);

        assertNotNull(result);
        verify(cinemaRepository).findById(cinemaId);
        verify(eventMappingService).mapDtoToEntity(dto);
        verify(eventRepository).save(any(Event.class));
        verify(timeFlagRepository).save(any(TimeFlag.class));
    }

    @Test
    void create_shouldCreateEventWithoutTimeFlags() {
        UUID orgId = UUID.randomUUID();
        UUID cinemaId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        EventCreateDto dto = new EventCreateDto(
            "Title", "Description", "http://img.jpg", "http://seance", LocalDateTime.now().plusDays(1),
            cinemaId, null
        );

        Cinema cinema = createMockCinema(cinemaId, orgId);
        Event event = createMockEvent();

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(cinema));
        when(eventMappingService.mapDtoToEntity(dto)).thenReturn(event);
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> {
            Event e = inv.getArgument(0);
            setEntityId(e, eventId);
            return e;
        });

        EventResponseDto result = eventService.create(dto, orgId);

        assertNotNull(result);
        verify(timeFlagRepository, never()).save(any(TimeFlag.class));
    }

    @Test
    void create_shouldThrowCinemaNotFoundExceptionWhenCinemaNotExists() {
        UUID orgId = UUID.randomUUID();
        UUID cinemaId = UUID.randomUUID();

        EventCreateDto dto = new EventCreateDto(
            "Title", "Description", "http://img.jpg", "http://seance", LocalDateTime.now().plusDays(1),
            cinemaId, null
        );

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.empty());

        assertThrows(CinemaNotFoundException.class, () -> eventService.create(dto, orgId));
    }

    @Test
    void create_shouldThrowForbiddenWhenOrganizationDoesNotOwnCinema() {
        UUID orgId = UUID.randomUUID();
        UUID otherOrgId = UUID.randomUUID();
        UUID cinemaId = UUID.randomUUID();

        EventCreateDto dto = new EventCreateDto(
            "Title", "Description", "http://img.jpg", "http://seance", LocalDateTime.now().plusDays(1),
            cinemaId, null
        );

        Cinema cinema = createMockCinema(cinemaId, otherOrgId);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(cinema));

        RestApiException ex = assertThrows(RestApiException.class, () -> eventService.create(dto, orgId));
        assertEquals(HttpStatus.FORBIDDEN, ex.getHttpStatus());
    }

    private Event createMockEvent() {
        UUID eventId = UUID.randomUUID();
        UUID cinemaId = UUID.randomUUID();

        Event event = new Event();
        setEntityId(event, eventId);
        event.setTitle("Test Event");
        event.setDescription("Description");
        event.setImageUrl("http://example.com/image.jpg");
        event.setSeanceLink("http://example.com/seance");
        event.setDatetime(LocalDateTime.now().plusDays(1));

        Cinema cinema = new Cinema();
        setEntityId(cinema, cinemaId);
        cinema.setName("Test Cinema");
        cinema.setAddress("Test Address");
        cinema.setOrganizationId(UUID.randomUUID());

        event.setCinema(cinema);
        return event;
    }

    private Cinema createMockCinema(UUID cinemaId, UUID organizationId) {
        Cinema cinema = new Cinema();
        setEntityId(cinema, cinemaId);
        cinema.setName("Test Cinema");
        cinema.setAddress("Test Address");
        cinema.setOrganizationId(organizationId);
        return cinema;
    }
}
