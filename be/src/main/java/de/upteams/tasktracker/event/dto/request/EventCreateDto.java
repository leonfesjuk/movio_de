package de.upteams.tasktracker.event.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Data Transfer Object for Event creation")
@Value
public class EventCreateDto {

    @Schema(description = "Title of the Event", example = "Film Premiere")
    @NotBlank
    String title;

    @Schema(description = "Description of the Event", example = "Premiere of new movie")
    @NotBlank
    String description;

    @Schema(description = "Image URL", example = "https://example.com/image.jpg")
    @NotBlank
    String imageUrl;

    @Schema(description = "Seance link", example = "https://cinema.com/seance/123")
    @NotBlank
    String seanceLink;

    @Schema(description = "Date and time of the event", example = "2026-04-20T18:00:00")
    @NotNull
    LocalDateTime datetime;

    @Schema(description = "Cinema UUID", example = "660e8400-e29b-41d4-a716-446655440001")
    @NotNull
    UUID cinemaId;

    @Schema(description = "Time flags")
    @NotNull
    TimeFlagDto timeFlags;

    @Value
    public static class TimeFlagDto {
        @Schema(description = "Time flag 1")
        Boolean timeFlag1;
        @Schema(description = "Time flag 2")
        Boolean timeFlag2;
        @Schema(description = "Time flag 3")
        Boolean timeFlag3;
    }
}
