package de.upteams.tasktracker.geonames.controller;

import de.upteams.tasktracker.geonames.controller.api.GeonameApi;
import de.upteams.tasktracker.geonames.dto.GeonameDetailsDto;
import de.upteams.tasktracker.geonames.dto.GeonameSearchListResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameStandardResponseDto;
import de.upteams.tasktracker.geonames.service.interfaces.GeonameService;
import de.upteams.tasktracker.security.service.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeonameController implements GeonameApi {

    private final GeonameService service;

    @Override
    public GeonameStandardResponseDto<GeonameSearchListResponseDto> searchCities(String query, Integer limit, String countryCode, AuthUserDetails currentUser) {
        GeonameSearchListResponseDto data = service.searchCities(query, limit, countryCode, currentUser != null ? currentUser.user() : null);
        return GeonameStandardResponseDto.success(data);
    }

    @Override
    public GeonameStandardResponseDto<GeonameDetailsDto> getCityDetails(Long geonameId, AuthUserDetails currentUser) {
        GeonameDetailsDto data = service.getCityDetails(geonameId, currentUser != null ? currentUser.user() : null);
        return GeonameStandardResponseDto.success(data);
    }
}
