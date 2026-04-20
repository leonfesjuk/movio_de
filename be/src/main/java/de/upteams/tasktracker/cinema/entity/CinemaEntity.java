package de.upteams.tasktracker.cinema.entity;

import de.upteams.tasktracker.geonames.entity.GeonameEntity;
import de.upteams.tasktracker.utils.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cinemas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaEntity extends BaseUuidEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name = "web_link", columnDefinition = "TEXT", nullable = false)
    private String webLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geonameid", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private GeonameEntity geoname;

    @Column(name = "organization_id")
    private Long organizationId;

    @Override
    public String toString() {
        return "CinemaEntity{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", webLink='" + webLink + '\'' +
                ", geonameId=" + (geoname == null ? "null" : geoname.getId()) +
                ", organizationId=" + organizationId +
                '}';
    }
}
