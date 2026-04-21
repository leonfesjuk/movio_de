package de.upteams.tasktracker.event.dto.response;

import de.upteams.tasktracker.cinema.dto.response.CinemaResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Data Transfer Object for Event response")
@Value
public class EventResponseDto {

    @Schema(description = "Unique identifier", example = "770e8400-e29b-41d4-a716-446655440002")
    UUID id;

    @Schema(description = "Title")
    String title;

    @Schema(description = "Description")
    String description;

    @Schema(description = "Image URL")
    String imageUrl;

    @Schema(description = "Seance link")
    String seanceLink;

    @Schema(description = "Date and time")
    LocalDateTime datetime;

    @Schema(description = "Cinema info")
    CinemaResponseDto cinema;

    @Schema(description = "Time flags")
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
