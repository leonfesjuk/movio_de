package de.upteams.tasktracker.geonames.dto;

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
    private List<GeonameResponseDto> items;
}
