package de.upteams.tasktracker.event.entity;

import de.upteams.tasktracker.utils.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "time_flags")
@Getter
@Setter
@NoArgsConstructor
public class TimeFlag extends BaseUuidEntity {

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "time_flag_1", nullable = false)
    private Boolean timeFlag1;

    @Column(name = "time_flag_2", nullable = false)
    private Boolean timeFlag2;

    @Column(name = "time_flag_3", nullable = false)
    private Boolean timeFlag3;

    public TimeFlag(UUID eventId, Boolean timeFlag1, Boolean timeFlag2, Boolean timeFlag3) {
        this.eventId = eventId;
        this.timeFlag1 = timeFlag1;
        this.timeFlag2 = timeFlag2;
        this.timeFlag3 = timeFlag3;
    }

    @Override
    public String toString() {
        return "TimeFlag{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", timeFlag1=" + timeFlag1 +
                ", timeFlag2=" + timeFlag2 +
                ", timeFlag3=" + timeFlag3 +
                '}';
    }
}
