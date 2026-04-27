package de.upteams.tasktracker.testdata.invitetoken;

import de.upteams.tasktracker.invitetoken.entity.InviteToken;
import de.upteams.tasktracker.invitetoken.persistence.InviteTokenRepository;
import de.upteams.tasktracker.testdata.core.SeedCommand;
import de.upteams.tasktracker.testdata.core.SeedResult;
import de.upteams.tasktracker.testdata.core.TestDataSeeder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteTokenSeedService implements TestDataSeeder {

    private final InviteTokenRepository inviteTokenRepository;

    @Override
    public String name() {
        return "invite-token";
    }

    @Override
    @Transactional
    public SeedResult seed(SeedCommand command) {
        int count = command.count() == null ? 20 : command.count();
        boolean markSomeAsUsed = Boolean.parseBoolean(
                command.options().getOrDefault("markSomeAsUsed", "true")
        );

        if (count <= 0) {
            return new SeedResult(name(), "skipped", 0, inviteTokenRepository.count(), "count <= 0");
        }

        if (command.skipIfNotEmpty() && inviteTokenRepository.count() > 0) {
            return new SeedResult(name(), "skipped", 0, inviteTokenRepository.count(), "table is not empty");
        }

        Instant now = Instant.now();
        List<InviteToken> tokens = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            InviteToken token = InviteToken.builder()
                    .token("seed-token-" + UUID.randomUUID())
                    .build();

            if (markSomeAsUsed && i % 3 == 0) {
                token.setUsedAt(now.minusSeconds((long) i * 60));
            }

            tokens.add(token);
        }

        inviteTokenRepository.saveAll(tokens);
        return new SeedResult(name(), "ok", tokens.size(), inviteTokenRepository.count(), "created");
    }
}