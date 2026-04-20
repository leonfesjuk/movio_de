package de.upteams.tasktracker.geonames.service.interfaces;

import de.upteams.tasktracker.geonames.dto.GeonameDetailsDto;
import de.upteams.tasktracker.geonames.dto.GeonameSearchListResponseDto;
import de.upteams.tasktracker.user.entity.AppUser;

public interface GeonameService {
    GeonameSearchListResponseDto searchCities(String query, Integer limit, String countryCode, AppUser currentUser);
    GeonameDetailsDto getCityDetails(Long geonameId, AppUser currentUser);
}
