package de.upteams.tasktracker.testdata.core;

import de.upteams.tasktracker.testdata.core.dto.SeedRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class SeedDefaultsFactory {

    private static final String DEFAULT_PRESET = "mvp";

    private static final Map<String, String> PRESET_ALIASES = Map.of(
            "1", "smoke",
            "2", "mvp",
            "3", "full"
    );

    private static final Set<String> PRESET_NAMES = Set.of("smoke", "mvp", "full");

    private static final Map<String, Map<String, SeedCommand>> PRESETS = Map.of(
            "smoke", Map.of(
                    "invite-token", new SeedCommand(6, true, Map.of("markSomeAsUsed", "true")),
                    "user", new SeedCommand(3, true, Map.of()),
                    "cinema", new SeedCommand(5, true, Map.of("geonamePoolLimit", "120")),
                    "event", new SeedCommand(12, true, Map.of())
            ),
            "mvp", Map.of(
                    "invite-token", new SeedCommand(20, true, Map.of("markSomeAsUsed", "true")),
                    "user", new SeedCommand(5, true, Map.of()),
                    "cinema", new SeedCommand(20, true, Map.of("geonamePoolLimit", "500")),
                    "event", new SeedCommand(40, true, Map.of())
            ),
            "full", Map.of(
                    "invite-token", new SeedCommand(60, true, Map.of("markSomeAsUsed", "true")),
                    "user", new SeedCommand(5, true, Map.of()),
                    "cinema", new SeedCommand(60, true, Map.of("geonamePoolLimit", "1000")),
                    "event", new SeedCommand(120, true, Map.of())
            )
    );

    public SeedCommand toSingleSeederCommand(SeedRequest request, int defaultCount, Map<String, String> defaultOptions) {
        if (request == null) {
            return new SeedCommand(defaultCount, false, defaultOptions);
        }

        return new SeedCommand(
                request.count() == null ? defaultCount : request.count(),
                request.skipIfNotEmpty() != null && request.skipIfNotEmpty(),
                request.options() == null ? defaultOptions : request.options()
        );
    }

    public SeedCommand toAllSeederCommand(String seederName, String preset) {
        String resolvedPreset = resolvePreset(preset);
        Map<String, SeedCommand> config = PRESETS.getOrDefault(resolvedPreset, PRESETS.get(DEFAULT_PRESET));
        return config.getOrDefault(seederName, new SeedCommand(20, true, Map.of()));
    }

    public String resolvePreset(String preset) {
        if (preset == null || preset.isBlank()) {
            return DEFAULT_PRESET;
        }

        String raw = preset.trim().toLowerCase();
        if (PRESET_ALIASES.containsKey(raw)) {
            return PRESET_ALIASES.get(raw);
        }

        if (PRESET_NAMES.contains(raw)) {
            return raw;
        }

        return DEFAULT_PRESET;
    }

    public String resolvePreset(SeedRequest request) {
        if (request == null) {
            return DEFAULT_PRESET;
        }
        return resolvePreset(request.preset());
    }
}
