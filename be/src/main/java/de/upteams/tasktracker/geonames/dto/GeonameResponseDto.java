package de.upteams.tasktracker.geonames.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeonameResponseDto {
    @Schema(description = "Unique identifier for the geoname", example = "2950159")
    private Long geonameId;

    @Schema(description = "Name of the geoname", example = "Berlin")
    private String name;

    @Schema(description = "ISO 3166-1 alpha-2 country code", example = "DE")
    private String countryCode;

    @Schema(description = "Latitude coordinate", example = "52.52437")
    private Double latitude;

    @Schema(description = "Longitude coordinate", example = "13.41053")
    private Double longitude;
}
