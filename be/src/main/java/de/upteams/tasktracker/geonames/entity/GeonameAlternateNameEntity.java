package de.upteams.tasktracker.geonames.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "geonames_alternate_names")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeonameAlternateNameEntity {

    @Id
    @Column(name = "alternate_name_id")
    private Long alternateNameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geonameid", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private GeonameEntity geoname;

    @Column(name = "isolanguage", length = 7)
    private String isoLanguage;

    @Column(name = "name", length = 400)
    private String name;

    @Column(name = "is_preferred")
    private Boolean isPreferred;
}
