package de.upteams.tasktracker.geonames.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeonameAlternateNameDto {
    private String code;
    private String name;
    private Boolean isPreferred;
}
