package de.upteams.tasktracker.user.dto.request;

import de.upteams.tasktracker.user.util.validation.password.ValidPassword;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequestDto(
        @NotBlank(message = "{user.password.notBlank}")
        String token,

        @ValidPassword
        String newPassword
) {}
