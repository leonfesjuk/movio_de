package de.upteams.tasktracker.cinema.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.util.List;

@Schema(description = "Paginated list of cinemas")
@Value
public class CinemaListDto {

    @Schema(description = "List of cinemas")
    List<CinemaResponseDto> items;

    @Schema(description = "Pagination info")
    PaginationDto pagination;

    @Value
    public static class PaginationDto {
        @Schema(description = "Whether there are more items available")
        boolean hasMore;
    }
}
