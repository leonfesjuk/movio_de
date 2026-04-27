package de.upteams.tasktracker.geonames.service.impl;

import de.upteams.tasktracker.geonames.dto.GeonameDetailsDto;
import de.upteams.tasktracker.geonames.dto.GeonameResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameSearchListResponseDto;
import de.upteams.tasktracker.geonames.entity.GeonameEntity;
import de.upteams.tasktracker.geonames.persistence.GeonameRepository;
import de.upteams.tasktracker.geonames.utils.GeonameMapper;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeonameServiceImplTest {

    @Mock
    private GeonameRepository repository;

    @Mock
    private GeonameMapper mapper;

    @InjectMocks
    private GeonameServiceImpl service;

    private GeonameEntity testEntity;
    private GeonameResponseDto testResponseDto;
    private GeonameDetailsDto testDetailsDto;
    private List<GeonameEntity> testEntities;

    @BeforeEach
    void setUp() throws Exception {
        testEntity = GeonameEntity.builder()
                .name("Berlin")
                .asciiName("Berlin")
                .latitude(52.52437)
                .longitude(13.41053)
                .countryCode("DE")
                .population(3426354L)
                .timezone("Europe/Berlin")
                .isActive(true)
                .build();
        var field = de.upteams.tasktracker.utils.GeoBaseEntity.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(testEntity, 2950159L);

        testResponseDto = GeonameResponseDto.builder()
                .geonameId(2950159L)
                .name("Berlin")
                .countryCode("DE")
                .latitude(52.52437)
                .longitude(13.41053)
                .build();

        testDetailsDto = GeonameDetailsDto.builder()
                .geonameId(2950159L)
                .name("Berlin")
                .countryCode("DE")
                .latitude(52.52437)
                .longitude(13.41053)
                .population(3426354L)
                .timezone("Europe/Berlin")
                .build();

        testEntities = List.of(testEntity);
    }

    @Test
    void searchCities_shouldCallSearchAllForRegularUser() {
        AppUser regularUser = new AppUser("password", "email@test.com", "testuser", "http://example.com");
        regularUser.setRole(Role.ROLE_USER);

        when(repository.searchAll(eq("Berlin"), any(Pageable.class))).thenReturn(testEntities);
        when(mapper.toGeonameResponseDtoList(testEntities)).thenReturn(List.of(testResponseDto));
        when(mapper.toGeonameSearchListResponseDto(List.of(testResponseDto)))
                .thenReturn(GeonameSearchListResponseDto.builder().items(List.of(testResponseDto)).build());

        GeonameSearchListResponseDto result = service.searchCities("Berlin", 10, null, regularUser);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals("Berlin", result.getItems().get(0).getName());
        verify(repository).searchAll(eq("Berlin"), any(Pageable.class));
    }

    @Test
    void searchCities_shouldCallSearchWithCinemasForAdmin() {
        AppUser adminUser = new AppUser("password", "admin@test.com", "admin", "http://example.com");
        adminUser.setRole(Role.ROLE_ADMIN);

        when(repository.searchWithCinemas(eq("Berlin"), any(Pageable.class))).thenReturn(testEntities);
        when(mapper.toGeonameResponseDtoList(testEntities)).thenReturn(List.of(testResponseDto));
        when(mapper.toGeonameSearchListResponseDto(List.of(testResponseDto)))
                .thenReturn(GeonameSearchListResponseDto.builder().items(List.of(testResponseDto)).build());

        GeonameSearchListResponseDto result = service.searchCities("Berlin", 10, null, adminUser);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        verify(repository).searchWithCinemas(eq("Berlin"), any(Pageable.class));
    }

    @Test
    void searchCities_shouldCallSearchWithCinemasForNullUser() {
        when(repository.searchWithCinemas(eq("Berlin"), any(Pageable.class))).thenReturn(testEntities);
        when(mapper.toGeonameResponseDtoList(testEntities)).thenReturn(List.of(testResponseDto));
        when(mapper.toGeonameSearchListResponseDto(List.of(testResponseDto)))
                .thenReturn(GeonameSearchListResponseDto.builder().items(List.of(testResponseDto)).build());

        GeonameSearchListResponseDto result = service.searchCities("Berlin", 10, null, null);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        verify(repository).searchWithCinemas(eq("Berlin"), any(Pageable.class));
    }

    @Test
    void searchCities_shouldUseDefaultLimitWhenNull() {
        AppUser adminUser = new AppUser("password", "admin@test.com", "admin", "http://example.com");
        adminUser.setRole(Role.ROLE_ADMIN);

        when(repository.searchWithCinemas(eq("Berlin"), any(Pageable.class))).thenReturn(testEntities);
        when(mapper.toGeonameResponseDtoList(testEntities)).thenReturn(List.of(testResponseDto));
        when(mapper.toGeonameSearchListResponseDto(List.of(testResponseDto)))
                .thenReturn(GeonameSearchListResponseDto.builder().items(List.of(testResponseDto)).build());

        GeonameSearchListResponseDto result = service.searchCities("Berlin", null, null, adminUser);

        assertNotNull(result);
        verify(repository).searchWithCinemas(eq("Berlin"), eq(PageRequest.of(0, 10)));
    }

    @Test
    void searchCities_shouldUseProvidedLimit() {
        AppUser adminUser = new AppUser("password", "admin@test.com", "admin", "http://example.com");
        adminUser.setRole(Role.ROLE_ADMIN);

        when(repository.searchWithCinemas(eq("Berlin"), eq(PageRequest.of(0, 20)))).thenReturn(testEntities);
        when(mapper.toGeonameResponseDtoList(testEntities)).thenReturn(List.of(testResponseDto));
        when(mapper.toGeonameSearchListResponseDto(List.of(testResponseDto)))
                .thenReturn(GeonameSearchListResponseDto.builder().items(List.of(testResponseDto)).build());

        GeonameSearchListResponseDto result = service.searchCities("Berlin", 20, null, adminUser);

        assertNotNull(result);
        verify(repository).searchWithCinemas(eq("Berlin"), eq(PageRequest.of(0, 20)));
    }

    @Test
    void getCityDetails_shouldReturnDetailsWhenFound() {
        when(repository.findById(2950159L)).thenReturn(Optional.of(testEntity));
        when(mapper.toGeonameDetailsDto(testEntity)).thenReturn(testDetailsDto);

        GeonameDetailsDto result = service.getCityDetails(2950159L, null);

        assertNotNull(result);
        assertEquals(2950159L, result.getGeonameId());
        assertEquals("Berlin", result.getName());
        assertEquals("DE", result.getCountryCode());
        assertEquals(3426354L, result.getPopulation());
        assertEquals("Europe/Berlin", result.getTimezone());
    }

    @Test
    void getCityDetails_shouldThrowExceptionWhenNotFound() {
        when(repository.findById(999999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.getCityDetails(999999L, null));

        assertEquals("City not found", exception.getMessage());
    }

    @Test
    void searchCities_shouldReturnEmptyListWhenNoResults() {
        AppUser adminUser = new AppUser("password", "admin@test.com", "admin", "http://example.com");
        adminUser.setRole(Role.ROLE_ADMIN);

        when(repository.searchWithCinemas(eq("NonExistent"), any(Pageable.class))).thenReturn(List.of());
        when(mapper.toGeonameResponseDtoList(List.of())).thenReturn(List.of());
        when(mapper.toGeonameSearchListResponseDto(List.of()))
                .thenReturn(GeonameSearchListResponseDto.builder().items(List.of()).build());

        GeonameSearchListResponseDto result = service.searchCities("NonExistent", 10, null, adminUser);

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
    }
}