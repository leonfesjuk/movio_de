package de.upteams.tasktracker.cinema.entity;

import de.upteams.tasktracker.geonames.entity.GeonameEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cinemas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
