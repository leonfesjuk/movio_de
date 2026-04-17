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
public class GeonameSearchListResponseDto {
    @Schema(description = "List of geoname search results")
    private List<GeonameResponseDto> items;
}
