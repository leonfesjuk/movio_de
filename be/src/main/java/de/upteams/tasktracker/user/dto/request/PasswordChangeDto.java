package de.upteams.tasktracker.user.dto.request;

import de.upteams.tasktracker.user.util.validation.password.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for changing user password")
public record PasswordChangeDto(

        @Schema(description = "User's current password", example = "Qwerty1!")
        @NotBlank
        String currentPassword,

        @Schema(description = "User's new password", example = "Qwerty2!")
        @NotBlank
        @ValidPassword
        String newPassword,

        @Schema(description = "User's new password confirmation. Should match with new password", example = "Qwerty2!")
        @NotBlank
        String confirmPassword
) {
}
