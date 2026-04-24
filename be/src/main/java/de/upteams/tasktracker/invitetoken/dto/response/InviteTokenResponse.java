package de.upteams.tasktracker.invitetoken.dto.response;

import java.time.Instant;
import java.util.UUID;

public record InviteTokenResponse(
        String token,
        Instant createdAt,
        Instant usedAt,
        boolean used
) {}
