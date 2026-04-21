package de.upteams.tasktracker.event.entity;

import de.upteams.tasktracker.cinema.entity.Cinema;
import de.upteams.tasktracker.utils.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "image_url", columnDefinition = "TEXT", nullable = false)
    private String imageUrl;

    @Column(name = "seance_link", columnDefinition = "TEXT", nullable = false)
    private String seanceLink;

    @Column(nullable = false)
    private LocalDateTime datetime;

    public Event(Cinema cinema, String title, String description,
                 String imageUrl, String seanceLink, LocalDateTime datetime) {
        this.cinema = cinema;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.seanceLink = seanceLink;
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", cinema=" + (cinema != null ? cinema.getId() : "null") +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", seanceLink='" + seanceLink + '\'' +
                ", datetime=" + datetime +
                '}';
    }
}

