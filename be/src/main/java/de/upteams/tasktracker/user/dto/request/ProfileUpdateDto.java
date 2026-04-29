package de.upteams.tasktracker.user.dto.request;

import de.upteams.tasktracker.user.util.validation.url.ValidUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for updating user profile")
public record ProfileUpdateDto(
        @Schema(
                description = "Organization website (http/https will be normalized automatically)",
                example = "https://cinemaxx.com"
        )
        @NotBlank(message = "{user.webLink.notBlank}")
        @ValidUrl
        String webLink
) {}