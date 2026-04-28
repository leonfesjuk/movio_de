package de.upteams.tasktracker.testdata.core.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Map;

@Schema(description = "Common request payload for test-data generation")
public record SeedRequest(
        @Schema(
                description = "How many entities to generate",
                example = "20",
                minimum = "1",
                maximum = "10000"
        )
        @Min(1) @Max(10000) Integer count,

        @Schema(
                description = "Skip generation if target storage is not empty",
                example = "false"
        )
        Boolean skipIfNotEmpty,

        @Schema(
                description = "Seeder-specific options map",
                example = "{\"markSomeAsUsed\":\"true\"}"
        )
        Map<String, String> options,

        @Schema(
                description = "Preset key for /seed/all. Supported names: smoke, mvp, full. Also numbers: 1, 2, 3",
                example = "mvp"
        )
        String preset
) {
}
