package de.upteams.tasktracker.event.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;

@Schema(description = "Data Transfer Object for Event list response")
@Value
public class EventListDto {

    @Schema(description = "List of events")
    List<EventResponseDto> items;

    @Schema(description = "Pagination info")
    PaginationDto pagination;

    @Value
    public static class PaginationDto {
        @Schema(description = "Has more pages")
        Boolean hasMore;
    }
}
