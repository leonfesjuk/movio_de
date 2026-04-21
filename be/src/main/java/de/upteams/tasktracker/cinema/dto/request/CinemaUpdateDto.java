package de.upteams.tasktracker.cinema.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Schema(description = "Data Transfer Object for updating a Cinema")
public class CinemaUpdateDto {

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255)
    @Schema(description = "Name of the cinema", example = "Cinema Star Premium")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 255)
    @Schema(description = "Address of the cinema", example = "New St. 20")
    private String address;

    @NotBlank(message = "Web link is required")
    @URL
    @Size(min = 5, max = 2048)
    @Schema(description = "Web link of the cinema", example = "https://cinema-star-premium.com")
    private String webLink;

    @NotNull(message = "Geoname ID is required")
    @Schema(description = "Geoname ID of the city", example = "2950159")
    private Long geonameId;
}
