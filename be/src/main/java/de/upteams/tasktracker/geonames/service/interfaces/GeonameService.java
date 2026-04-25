package de.upteams.tasktracker.geonames.service.interfaces;

import de.upteams.tasktracker.geonames.dto.GeonameAlternateNamesResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameCountryDto;
import de.upteams.tasktracker.geonames.dto.GeonameDetailsDto;
import de.upteams.tasktracker.geonames.dto.GeonameSearchListResponseDto;
import de.upteams.tasktracker.user.entity.AppUser;

import java.util.List;

public interface GeonameService {
    GeonameSearchListResponseDto searchCities(String query, Integer limit, String countryCode, AppUser currentUser);
    GeonameDetailsDto getCityDetails(Long geonameId, AppUser currentUser);
    List<GeonameCountryDto> getCountries();
    GeonameAlternateNamesResponseDto getAlternateNames(Long geonameId);
}
