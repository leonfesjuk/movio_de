package de.upteams.tasktracker.cinema.entity;

import de.upteams.tasktracker.geonames.entity.GeonameEntity;
import de.upteams.tasktracker.utils.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "cinemas")
@Getter
@Setter
@NoArgsConstructor
public class Cinema extends BaseUuidEntity {

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(name = "geonameid", nullable = false)
    private Long geonameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "geonameid",
            referencedColumnName = "geonameid",
            insertable = false,
            updatable = false
    )
    private GeonameEntity geoname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name = "web_link", columnDefinition = "TEXT")
    private String webLink;

    public Cinema(UUID organizationId, Long geonameId, String name, String address, String webLink) {
        this.organizationId = organizationId;
        this.geonameId = geonameId;
        this.name = name;
        this.address = address;
        this.webLink = webLink;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", geonameId=" + geonameId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", webLink='" + webLink + '\'' +
                '}';
    }
}
