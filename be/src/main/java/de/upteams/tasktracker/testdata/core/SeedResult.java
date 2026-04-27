package de.upteams.tasktracker.testdata.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Result of a single seeder execution")
public record SeedResult(
        @Schema(description = "Seeder key/name", example = "invite-token")
        String key,

        @Schema(description = "Execution status", example = "ok")
        String status,

        @Schema(description = "Number of created records", example = "20")
        int created,

        @Schema(description = "Total records in DB after execution", example = "120")
        long totalInDb,

        @Schema(description = "Additional execution message", example = "created")
        String message
) {
}
