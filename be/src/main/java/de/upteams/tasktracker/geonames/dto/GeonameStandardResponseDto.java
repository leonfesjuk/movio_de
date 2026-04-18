package de.upteams.tasktracker.geonames.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Standard successful API response wrapper")
public class GeonameStandardResponseDto<T> {
    @Schema(description = "Status of the response", example = "success")
    private String status;
    private T data;

    public static <T> GeonameStandardResponseDto<T> success(T data) {
        return GeonameStandardResponseDto.<T>builder()
                .status("success")
                .data(data)
                .build();
    }
}
