package de.upteams.tasktracker.geonames.entity;

import de.upteams.tasktracker.utils.GeoBaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "geonames_alternate_names")
@AttributeOverride(
        name = "id",
        column = @Column(name = "alternate_name_id", updatable = false, nullable = false)
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeonameAlternateNameEntity extends GeoBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geonameid", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private GeonameEntity geoname;

    @Column(name = "isolanguage", length = 7)
    private String isoLanguage;

    @Column(name = "name", length = 400)
    private String name;

    @Column(name = "is_preferred")
    private Boolean isPreferred;

    @Override
    public String toString() {
        return "GeonameAlternateNameEntity{" +
                "id=" + getId() +
                ", geonameId=" + (geoname == null ? "null" : geoname.getId()) +
                ", isoLanguage='" + isoLanguage + '\'' +
                ", name='" + name + '\'' +
                ", isPreferred=" + isPreferred +
                '}';
    }
}
