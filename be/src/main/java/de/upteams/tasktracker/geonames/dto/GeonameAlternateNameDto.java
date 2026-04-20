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
public class GeonameAlternateNameDto {
    @Schema(description = "Language code (ISO 639-1)", example = "en")
    private String code;

    @Schema(description = "Alternate name for the geoname", example = "Berlin")
    private String name;

    @Schema(description = "Indicates if this is the preferred name for the language", example = "false")
    private Boolean isPreferred;
}
