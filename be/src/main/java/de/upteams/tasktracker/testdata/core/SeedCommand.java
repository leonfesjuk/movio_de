package de.upteams.tasktracker.testdata.core;

import java.util.Map;

public record SeedCommand(
        Integer count,
        boolean skipIfNotEmpty,
        Map<String, String> options
) {
}
