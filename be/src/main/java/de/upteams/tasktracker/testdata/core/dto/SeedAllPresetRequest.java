package de.upteams.tasktracker.testdata.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Preset-only request for /internal/test-data/seed/all")
public record SeedAllPresetRequest(
        @Schema(
                description = "Preset key. Supported values: smoke, mvp, full, 1, 2, 3",
                example = "mvp"
        )
        String preset
) {
}
