package de.upteams.tasktracker.testdata.core.dto;

import de.upteams.tasktracker.testdata.core.SeedResult;

import java.util.List;
import java.util.UUID;

public record SeedAllResponse(
        List<SeedResult> results,
        String summary,
        GeneratedStructure structure
) {
    public record GeneratedStructure(
            List<UserNode> users
    ) {}

    public record UserNode(
            UUID id,
            String email,
            List<CinemaNode> cinemas
    ) {}

    public record CinemaNode(
            UUID id,
            String name,
            List<EventNode> events
    ) {}

    public record EventNode(
            UUID id,
            String title
    ) {}
}
