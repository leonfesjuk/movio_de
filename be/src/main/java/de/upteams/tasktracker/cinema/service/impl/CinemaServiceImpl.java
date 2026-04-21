package de.upteams.tasktracker.cinema.service.impl;

import de.upteams.tasktracker.cinema.dto.request.CinemaCreateDto;
import de.upteams.tasktracker.cinema.dto.request.CinemaUpdateDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaListDto;
import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import de.upteams.tasktracker.cinema.entity.Cinema;
import de.upteams.tasktracker.cinema.exception.CinemaNotFoundException;
import de.upteams.tasktracker.cinema.persistence.CinemaRepository;
import de.upteams.tasktracker.cinema.service.interfaces.CinemaService;
import de.upteams.tasktracker.cinema.utils.CinemaMappingService;
import de.upteams.tasktracker.event.persistence.EventRepository;
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
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    private final EventRepository eventRepository;
    private final CinemaMappingService cinemaMappingService;

    @Override
    public CinemaResponseDto getById(UUID id, UUID organizationId) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new CinemaNotFoundException(id.toString()));

        validateOwnership(cinema, organizationId);

        return cinemaMappingService.mapEntityToDto(cinema);
    }

    @Override
    public CinemaListDto getAll(int page, int size, String city, UUID organizationId) {
        PageRequest pageable = PageRequest.of(page, size);
        
        // Note: 'city' filter is ignored for now as we don't have Geonames mapping yet
        Page<Cinema> cinemaPage = cinemaRepository.findByOrganizationId(organizationId, pageable);

        List<CinemaResponseDto> items = cinemaPage.getContent().stream()
                .map(cinemaMappingService::mapEntityToDto)
                .collect(Collectors.toList());

        return new CinemaListDto(items, new CinemaListDto.PaginationDto(cinemaPage.hasNext()));
    }

    @Override
    @Transactional
    public CinemaResponseDto create(CinemaCreateDto dto, UUID organizationId) {
        Cinema cinema = new Cinema(
                organizationId,
                dto.getGeonameId(),
                dto.getName(),
                dto.getAddress(),
                dto.getWebLink()
        );

        Cinema savedCinema = cinemaRepository.save(cinema);
        return cinemaMappingService.mapEntityToDto(savedCinema);
    }

    @Override
    @Transactional
    public CinemaResponseDto update(UUID id, CinemaUpdateDto dto, UUID organizationId) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new CinemaNotFoundException(id.toString()));

        validateOwnership(cinema, organizationId);

        cinema.setName(dto.getName());
        cinema.setAddress(dto.getAddress());
        cinema.setWebLink(dto.getWebLink());
        cinema.setGeonameId(dto.getGeonameId());

        Cinema savedCinema = cinemaRepository.save(cinema);
        return cinemaMappingService.mapEntityToDto(savedCinema);
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID organizationId) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new CinemaNotFoundException(id.toString()));

        validateOwnership(cinema, organizationId);

        if (eventRepository.existsByCinemaId(id)) {
            throw new RestApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Cannot delete cinema with planned events");
        }

        cinemaRepository.delete(cinema);
    }

    private void validateOwnership(Cinema cinema, UUID organizationId) {
        if (!cinema.getOrganizationId().equals(organizationId)) {
            throw new RestApiException(HttpStatus.FORBIDDEN, "Access denied to this cinema");
        }
    }
}
