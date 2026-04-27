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
@Schema(description = "Country info for geonames")
public class GeonameCountryDto {
    @Schema(description = "ISO 3166-1 alpha-2 country code", example = "DE")
    private String code;

    @Schema(description = "Country name", example = "Germany")
    private String name;
}