package de.upteams.tasktracker.cinema.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.UUID;

@Schema(description = "Data Transfer Object for Cinema response")
@Value
public class CinemaResponseDto {

    @Schema(description = "Unique identifier", example = "660e8400-e29b-41d4-a716-446655440001")
    UUID id;

    @Schema(description = "Name")
    String name;

    @Schema(description = "Address")
    String address;

    @Schema(description = "City name")
    String cityName;
}
