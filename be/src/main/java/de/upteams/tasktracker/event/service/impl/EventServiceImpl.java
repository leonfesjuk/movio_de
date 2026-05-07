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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final TimeFlagRepository timeFlagRepository;
    private final CinemaRepository cinemaRepository;
    private final EventMappingService eventMappingService;
    private final TimeFlagMappingService timeFlagMappingService;

    @Override
    public EventResponseDto getById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id.toString()));
        return mapToResponseDto(event);
    }

    @Override
    public EventListDto getAll(UUID cinemaId, Long cityId, int page, int size, UUID organizationId) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Event> eventPage;

        if (cinemaId != null) {
            eventPage = eventRepository.findUpcomingEventsByCinema(cinemaId, pageable);
        } else if (cityId != null) {
            eventPage = eventRepository.findUpcomingEventsByCity(cityId, pageable);
        } else if (organizationId != null) {
            eventPage = eventRepository.findByOrganizationIdWithDateCheck(organizationId, pageable);
        } else {
            eventPage = eventRepository.findUpcomingEvents(pageable);
        }

        List<EventResponseDto> items = eventPage.getContent().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());

        return new EventListDto(items, new EventListDto.PaginationDto(eventPage.hasNext()));
    }

    @Override
    @Transactional
    public EventResponseDto create(EventCreateDto dto, UUID organizationId) {
        Cinema cinema = validateCinemaOwnership(dto.getCinemaId(), organizationId);

        Event event = eventMappingService.mapDtoToEntity(dto);
        event.setCinema(cinema);
        Event savedEvent = eventRepository.save(event);

        return mapToResponseDto(savedEvent);
    }

    @Override
    @Transactional
    public EventResponseDto update(UUID id, EventUpdateDto dto, UUID organizationId) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id.toString()));

        validateCinemaOwnership(event.getCinema().getId(), organizationId);

        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getImageUrl() != null) {
            event.setImageUrl(dto.getImageUrl());
        }
        if (dto.getSeanceLink() != null) {
            event.setSeanceLink(dto.getSeanceLink());
        }
        if (dto.getDatetime() != null) {
            event.setDatetime(dto.getDatetime());
        }
        if (dto.getCinemaId() != null) {
            Cinema newCinema = validateCinemaOwnership(dto.getCinemaId(), organizationId);
            event.setCinema(newCinema);
        }

        Event savedEvent = eventRepository.save(event);

        if (dto.getTimeFlags() != null) {
            TimeFlag timeFlag = timeFlagRepository.findByEventId(id)
                    .orElse(new TimeFlag(id, false, false, false));

            timeFlag.setTimeFlag1(dto.getTimeFlags().getTimeFlag1());
            timeFlag.setTimeFlag2(dto.getTimeFlags().getTimeFlag2());
            timeFlag.setTimeFlag3(dto.getTimeFlags().getTimeFlag3());

            timeFlagRepository.save(timeFlag);
        }

        return mapToResponseDto(savedEvent);
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID organizationId) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id.toString()));

        validateCinemaOwnership(event.getCinema().getId(), organizationId);

        timeFlagRepository.findByEventId(id).ifPresent(timeFlagRepository::delete);
        eventRepository.delete(event);
    }

    private Cinema validateCinemaOwnership(UUID cinemaId, UUID organizationId) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new CinemaNotFoundException(cinemaId.toString()));

        if (!cinema.getOrganizationId().equals(organizationId)) {
            throw new RestApiException(HttpStatus.FORBIDDEN, "You don't have access to this cinema");
        }

        return cinema;
    }

    private EventResponseDto mapToResponseDto(Event event) {
        TimeFlag timeFlag = timeFlagRepository.findByEventId(event.getId())
                .orElse(null);

        Cinema cinema = event.getCinema();

        return new EventResponseDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getImageUrl(),
                event.getSeanceLink(),
                event.getDatetime(),
                cinema != null ? new CinemaResponseDto(
                        cinema.getId(),
                        cinema.getName(),
                        cinema.getAddress(),
                        null
                ) : null,
                timeFlag != null ? new EventResponseDto.TimeFlagDto(
                        timeFlag.getTimeFlag1(),
                        timeFlag.getTimeFlag2(),
                        timeFlag.getTimeFlag3()
                ) : null
        );
    }
}
