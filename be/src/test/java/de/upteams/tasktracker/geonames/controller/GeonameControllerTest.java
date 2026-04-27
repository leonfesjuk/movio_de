package de.upteams.tasktracker.geonames.controller;

import de.upteams.tasktracker.geonames.dto.GeonameDetailsDto;
import de.upteams.tasktracker.geonames.dto.GeonameSearchListResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameStandardResponseDto;
import de.upteams.tasktracker.geonames.service.interfaces.GeonameService;
import de.upteams.tasktracker.security.service.AuthUserDetails;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeonameControllerTest {

    @Mock
    private GeonameService service;

    @InjectMocks
    private GeonameController controller;

    private GeonameSearchListResponseDto searchResponse;
    private GeonameDetailsDto detailsResponse;
    private AppUser testUser;
    private AuthUserDetails authUserDetails;

    @BeforeEach
    void setUp() {
        GeonameSearchListResponseDto.GeonameSearchListResponseDtoBuilder searchBuilder =
                GeonameSearchListResponseDto.builder();
        searchBuilder.items(List.of(
                de.upteams.tasktracker.geonames.dto.GeonameResponseDto.builder()
                        .geonameId(2950159L)
                        .name("Berlin")
                        .countryCode("DE")
                        .latitude(52.52437)
                        .longitude(13.41053)
                        .build()
        ));
        searchResponse = searchBuilder.build();

        detailsResponse = GeonameDetailsDto.builder()
                .geonameId(2950159L)
                .name("Berlin")
                .countryCode("DE")
                .latitude(52.52437)
                .longitude(13.41053)
                .population(3426354L)
                .timezone("Europe/Berlin")
                .build();

        testUser = new AppUser("password", "test@test.com", "testuser", "http://example.com");
        testUser.setRole(Role.ROLE_USER);
        authUserDetails = new AuthUserDetails(testUser);
    }

    @Test
    void searchCities_shouldReturnSuccessResponse() {
        when(service.searchCities("Berlin", 10, "DE", testUser)).thenReturn(searchResponse);

        GeonameStandardResponseDto<GeonameSearchListResponseDto> result =
                controller.searchCities("Berlin", 10, "DE", authUserDetails);

        assertNotNull(result);
        assertEquals("success", result.getStatus());
        assertNotNull(result.getData());
        assertEquals(1, result.getData().getItems().size());
        assertEquals("Berlin", result.getData().getItems().get(0).getName());
    }

    @Test
    void searchCities_shouldWorkWithNullUser() {
        when(service.searchCities("Berlin", 10, "DE", null)).thenReturn(searchResponse);

        GeonameStandardResponseDto<GeonameSearchListResponseDto> result =
                controller.searchCities("Berlin", 10, "DE", null);

        assertNotNull(result);
        assertEquals("success", result.getStatus());
    }

    @Test
    void searchCities_shouldPassQueryToService() {
        when(service.searchCities("Munich", 5, null, testUser)).thenReturn(searchResponse);

        controller.searchCities("Munich", 5, null, authUserDetails);
    }

    @Test
    void getCityDetails_shouldReturnSuccessResponse() {
        when(service.getCityDetails(2950159L, testUser)).thenReturn(detailsResponse);

        GeonameStandardResponseDto<GeonameDetailsDto> result =
                controller.getCityDetails(2950159L, authUserDetails);

        assertNotNull(result);
        assertEquals("success", result.getStatus());
        assertNotNull(result.getData());
        assertEquals(2950159L, result.getData().getGeonameId());
        assertEquals("Berlin", result.getData().getName());
        assertEquals("Europe/Berlin", result.getData().getTimezone());
    }

    @Test
    void getCityDetails_shouldWorkWithNullUser() {
        when(service.getCityDetails(2950159L, null)).thenReturn(detailsResponse);

        GeonameStandardResponseDto<GeonameDetailsDto> result =
                controller.getCityDetails(2950159L, null);

        assertNotNull(result);
        assertEquals("success", result.getStatus());
    }

    @Test
    void getCityDetails_shouldPassIdToService() {
        when(service.getCityDetails(12345L, testUser)).thenReturn(detailsResponse);

        controller.getCityDetails(12345L, authUserDetails);
    }
}