package de.upteams.tasktracker.mail.confirmation.code;

import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.utils.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static de.upteams.tasktracker.utils.EntityUtil.getIdForToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "confirm_code")
public class ConfirmationCode extends BaseEntity {

    @Setter
    @NotNull
    @Column(name = "expired", nullable = false)
    private LocalDateTime expiresAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private AppUser user;


    public ConfirmationCode(LocalDateTime expiresAt, AppUser user) {
        this.expiresAt = expiresAt;
        this.user = user;
    }

    @Override
    public String toString() {
        return "ConfirmationCode{" +
                "code=" + id +
                ", expired=" + expiresAt +
                ", appUserId=" + getIdForToString(user) +
                '}';
    }
}
