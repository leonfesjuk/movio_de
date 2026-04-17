package de.upteams.tasktracker.user.dto.response;

import java.util.Map;

public record ApiErrorResponse(
        String message,
        Map<String, String> errors
) {}
