package de.upteams.tasktracker.geonames.service.impl;

import de.upteams.tasktracker.geonames.dto.GeonameDetailsDto;
import de.upteams.tasktracker.geonames.dto.GeonameResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameSearchListResponseDto;
import de.upteams.tasktracker.geonames.entity.GeonameEntity;
import de.upteams.tasktracker.geonames.persistence.GeonameRepository;
import de.upteams.tasktracker.geonames.service.interfaces.GeonameService;
import de.upteams.tasktracker.geonames.utils.GeonameMapper;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeonameServiceImpl implements GeonameService {

    private final GeonameRepository repository;
    private final GeonameMapper mapper;

    @Override
    public GeonameSearchListResponseDto searchCities(String query, Integer limit, String countryCode, AppUser currentUser) {
        Pageable pageable = PageRequest.of(0, limit != null ? limit : 10);
        List<GeonameEntity> entities;

        if (currentUser != null && currentUser.getRole() == Role.ROLE_USER) {
            entities = repository.searchAll(query, pageable);
        } else {
            entities = repository.searchWithCinemas(query, pageable);
        }

        List<GeonameResponseDto> dtos = mapper.toGeonameResponseDtoList(entities);
        return mapper.toGeonameSearchListResponseDto(dtos);
    }

    @Override
    public GeonameDetailsDto getCityDetails(Long geonameId, AppUser currentUser) {
        GeonameEntity entity = repository.findById(geonameId)
                .orElseThrow(() -> new RuntimeException("City not found"));
        return mapper.toGeonameDetailsDto(entity);
    }
}
