package de.upteams.tasktracker.geonames.controller.api;

import de.upteams.tasktracker.geonames.dto.GeonameAlternateNamesResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameCountryDto;
import de.upteams.tasktracker.geonames.dto.GeonameDetailsDto;
import de.upteams.tasktracker.geonames.dto.GeonameSearchListResponseDto;
import de.upteams.tasktracker.geonames.dto.GeonameStandardResponseDto;
import de.upteams.tasktracker.security.service.AuthUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Geonames Controller", description = "Endpoints for searching and getting details of geonames (cities)")
@RequestMapping("/api/geonames")
public interface GeonameApi {

    @Operation(summary = "Search cities", description = "Search for cities based on a query string. Admins see all cities, users/guests see only cities with cinemas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeonameStandardResponseDto.class)))
    })
    @GetMapping("/search")
    GeonameStandardResponseDto<GeonameSearchListResponseDto> searchCities(
            @RequestParam(value = "q", required = false)
            @Parameter(description = "Search query (city name or ascii name)")
            String query,

            @RequestParam(value = "limit", defaultValue = "10")
            @Parameter(description = "Maximum number of results")
            Integer limit,

            @RequestParam(value = "countryCode", required = false)
            @Parameter(description = "Filter by ISO 3166-1 alpha-2 country code")
            String countryCode,

            @AuthenticationPrincipal
            @Parameter(hidden = true)
            AuthUserDetails currentUser
    );

    @Operation(summary = "Get location details", description = "Get full details of a geoname by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location details found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeonameStandardResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @GetMapping("/{geonameId}")
    GeonameStandardResponseDto<GeonameDetailsDto> getCityDetails(
            @PathVariable("geonameId")
            @Parameter(description = "Unique ID of the geoname")
            Long geonameId,

            @AuthenticationPrincipal
            @Parameter(hidden = true)
            AuthUserDetails currentUser
    );

    @Operation(summary = "Get list of countries", description = "Returns a list of unique country codes available in geonames.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Countries found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeonameStandardResponseDto.class)))
    })
    @GetMapping("/countries")
    GeonameStandardResponseDto<List<GeonameCountryDto>> getCountries();

    @Operation(summary = "Get alternate names for a location", description = "Returns alternate names for a geoname by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alternate names found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeonameStandardResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @GetMapping("/{geonameId}/alternate-names")
    GeonameStandardResponseDto<GeonameAlternateNamesResponseDto> getAlternateNames(
            @PathVariable("geonameId")
            @Parameter(description = "Unique ID of the geoname")
            Long geonameId
    );
}
