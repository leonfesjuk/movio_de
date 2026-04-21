package de.upteams.tasktracker.event.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Data Transfer Object for Event update")
@Value
public class EventUpdateDto {

    @Schema(description = "Title of the Event")
    String title;

    @Schema(description = "Description of the Event")
    String description;

    @Schema(description = "Image URL")
    String imageUrl;

    @Schema(description = "Seance link")
    String seanceLink;

    @Schema(description = "Date and time of the event")
    LocalDateTime datetime;

    @Schema(description = "Cinema UUID")
    UUID cinemaId;

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
