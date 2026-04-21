package de.upteams.tasktracker.user.entity;

import de.upteams.tasktracker.utils.BaseUuidEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.ColumnDefault;

/**
 * Application User entity
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_user")
public class AppUser extends BaseUuidEntity {

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "{user.email.notBlank}")
    @Email(message = "{user.email.invalid}")
    @Column(
            name = "email",
            unique = true,
            nullable = false
    )
    private String email;

    @NotBlank(message = "{user.name.notBlank}")
    @Column(
            name = "name",
            unique = true,
            nullable = false
    )
    private String name;

    @NotBlank(message = "{user.webLink.notBlank}")
    @Column(
            name = "web_link",
            nullable = false
    )
    private String webLink;

    @NotNull(message = "{field.notNull}")
    @Column(name = "confirm_status", nullable = false)
    @ColumnDefault("'UNCONFIRMED'")
    @Enumerated(EnumType.STRING)
    private ConfirmationStatus confirmationStatus = ConfirmationStatus.UNCONFIRMED;

    @NotNull(message = "{field.notNull}")
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public AppUser(String password, String email, String name, String webLink) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.webLink = webLink;
        this.role = Role.ROLE_USER;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", confirmationStatus=" + confirmationStatus +
                ", password='" + (StringUtils.isBlank(password) ? "null" : "*hidden*") + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", webLink='" + webLink + '\'' +
                ", role=" + role +
                '}';
    }
}
