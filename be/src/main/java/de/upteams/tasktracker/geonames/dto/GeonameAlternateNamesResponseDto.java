package de.upteams.tasktracker.geonames.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response wrapper for alternate names list")
public class GeonameAlternateNamesResponseDto {
    private List<GeonameAlternateNameDto> items;
}