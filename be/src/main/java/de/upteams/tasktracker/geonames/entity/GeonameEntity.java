package de.upteams.tasktracker.geonames.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "geonames_raw")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeonameEntity {

    @Id
    @Column(name = "geonameid", nullable = false)
    private Long geonameId;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "asciiname", columnDefinition = "TEXT")
    private String asciiName;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "feature_code", columnDefinition = "TEXT")
    private String featureCode;

    @Column(name = "country_code", length = 2)
    private String countryCode;

    @Column(name = "population")
    private Long population;

    @Column(name = "timezone", columnDefinition = "TEXT")
    private String timezone;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = false;
}
