package de.upteams.tasktracker.user.entity;

import de.upteams.tasktracker.utils.BaseUuidEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "password_reset_token",
        indexes = {
                @Index(name = "idx_password_reset_token", columnList = "token")
        }
)
public class PasswordResetToken extends BaseUuidEntity {

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private boolean used;

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + getId() +
                ", token='" + token + '\'' +
                '}';
    }
}
