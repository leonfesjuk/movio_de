package de.upteams.tasktracker.geonames.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeonameDetailsDto {
    private Long geonameId;
    private String name;
    private String countryCode;
    private Double latitude;
    private Double longitude;
    private Long population;
    private String timezone;
}
