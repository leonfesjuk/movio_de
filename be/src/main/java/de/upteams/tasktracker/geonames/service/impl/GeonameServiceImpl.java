package de.upteams.tasktracker.geonames.service.impl;

import de.upteams.tasktracker.geonames.dto.GeonameAlternateNameDto;
import de.upteams.tasktracker.geonames.dto.GeonameAlternateNamesResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameCountryDto;
import de.upteams.tasktracker.geonames.dto.GeonameDetailsDto;
import de.upteams.tasktracker.geonames.dto.GeonameResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameSearchListResponseDto;
import de.upteams.tasktracker.geonames.entity.GeonameAlternateNameEntity;
import de.upteams.tasktracker.geonames.entity.GeonameEntity;
import de.upteams.tasktracker.geonames.persistence.GeonameAlternateNameRepository;
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
    private final GeonameAlternateNameRepository alternateNameRepository;
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

    @Override
    public List<GeonameCountryDto> getCountries() {
        return repository.findDistinctCountries().stream()
                .map(countryCode -> new GeonameCountryDto(countryCode, getCountryName(countryCode)))
                .toList();
    }

    @Override
    public GeonameAlternateNamesResponseDto getAlternateNames(Long geonameId) {
        if (!repository.existsById(geonameId)) {
            throw new RuntimeException("City not found");
        }
        
        List<GeonameAlternateNameEntity> alternateNames = alternateNameRepository.findByGeonameId(geonameId);
        List<GeonameAlternateNameDto> dtos = alternateNames.stream()
                .map(entity -> GeonameAlternateNameDto.builder()
                        .code(entity.getIsoLanguage())
                        .name(entity.getName())
                        .isPreferred(entity.getIsPreferred())
                        .build())
                .toList();
        
        return GeonameAlternateNamesResponseDto.builder()
                .items(dtos)
                .build();
    }

    private String getCountryName(String countryCode) {
        return switch (countryCode) {
            case "DE" -> "Germany";
            case "UA" -> "Ukraine";
            case "US" -> "United States";
            case "GB" -> "United Kingdom";
            case "FR" -> "France";
            case "ES" -> "Spain";
            case "IT" -> "Italy";
            case "PL" -> "Poland";
            case "RU" -> "Russia";
            default -> countryCode;
        };
    }
}
