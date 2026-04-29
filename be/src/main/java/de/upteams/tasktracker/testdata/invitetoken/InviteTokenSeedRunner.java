package de.upteams.tasktracker.testdata.invitetoken;

import de.upteams.tasktracker.invitetoken.persistence.InviteTokenRepository;
import de.upteams.tasktracker.testdata.core.SeedCommand;
import de.upteams.tasktracker.testdata.core.SeedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@Profile("dev")
@ConditionalOnProperty(
        name = "testdata.invite-token.runner.enabled",
        havingValue = "true",
        matchIfMissing = false
)
@RequiredArgsConstructor
public class InviteTokenSeedRunner implements CommandLineRunner {

    private final InviteTokenSeedService seedService;
    private final InviteTokenRepository inviteTokenRepository;

    @Override
    public void run(String... args) {
        // Защита от дублей при повторных стартах
        if (inviteTokenRepository.count() > 0) {
            log.info("InviteToken seeding skipped: table already contains data");
            return;
        }

        int count = 20;              // сколько токенов создать
        boolean markSomeAsUsed = true;

        SeedCommand command = new SeedCommand(
                count,
                true,
                Map.of("markSomeAsUsed", String.valueOf(markSomeAsUsed))
        );

        SeedResult result = seedService.seed(command);
        log.info("InviteToken seeding finished: status={}, created={}, totalInDb={}",
                result.status(), result.created(), result.totalInDb());
    }
}
