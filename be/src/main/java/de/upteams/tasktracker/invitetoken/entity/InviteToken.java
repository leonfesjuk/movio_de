package de.upteams.tasktracker.invitetoken.entity;

import de.upteams.tasktracker.utils.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "invite_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteToken extends BaseUuidEntity {

    @Column(name = "token", nullable = false, unique = true, length = 255)
    private String token;

    @Column(name = "used_at")
    private Instant usedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    public boolean isUsed() {
        return usedAt != null;
    }

    @Override
    public String toString() {
        return "InviteToken{" +
                "token='" + token + '\'' +
                ", usedAt=" + usedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}