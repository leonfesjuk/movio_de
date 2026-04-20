package de.upteams.tasktracker.user.dto.request;

import de.upteams.tasktracker.validation.password.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
        @Schema(
                description = "New user's email",
                example = "example@email.net"
        )
        @NotBlank(message = "{user.email.notBlank}")
        @Email(message = "{user.email.invalid}")
        String email,

        @Schema(
                description = "New user's password",
                example = "Qwerty1!"
        )
        @NotBlank(message = "{user.password.notBlank}")
        @ValidPassword
        String password,

        @Schema(
                description = "Organization identifier",
                example = "CinemaxX"
        )
        @NotBlank(message = "{user.name.notBlank}")
        String name,

        @Schema(
                description = "Organization website (http/https will be normalized automatically)",
                example = "https://cinemaxx.com"
        )
        @NotBlank(message = "{user.webLink.notBlank}")
        String webLink
) {
}
