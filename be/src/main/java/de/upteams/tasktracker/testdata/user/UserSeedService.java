package de.upteams.tasktracker.testdata.user;

import de.upteams.tasktracker.invitetoken.entity.InviteToken;
import de.upteams.tasktracker.invitetoken.persistence.InviteTokenRepository;
import de.upteams.tasktracker.testdata.core.SeedCommand;
import de.upteams.tasktracker.testdata.core.SeedResult;
import de.upteams.tasktracker.testdata.core.TestDataSeeder;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.entity.ConfirmationStatus;
import de.upteams.tasktracker.user.entity.Role;
import de.upteams.tasktracker.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSeedService implements TestDataSeeder {

    private static final String DEFAULT_PASSWORD = "password123";

    private final UserRepository userRepository;
    private final InviteTokenRepository inviteTokenRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String[] NAMES = {
            "alice", "bob", "charlie", "david", "emma",
            "frank", "grace", "harry", "irene", "jack"
    };

    @Override
    public String name() {
        return "user";
    }

    @Override
    @Transactional
    public SeedResult seed(SeedCommand command) {
        int requested = command.count() == null ? 5 : command.count();
        int count = Math.max(3, Math.min(5, requested));

        if (command.skipIfNotEmpty() && userRepository.count() > 0) {
            return new SeedResult(name(), "skipped", 0, userRepository.count(), "table is not empty");
        }

        List<AppUser> users = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            String baseName = NAMES[i % NAMES.length];
            String suffix = i + UUID.randomUUID().toString().replace("-", "").substring(0, 8);

            InviteToken inviteToken = InviteToken.builder()
                    .token("seed-user-token-" + suffix)
                    .build();
            inviteTokenRepository.save(inviteToken);

            AppUser user = new AppUser();
            user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            user.setEmail("test" + baseName + suffix + "@example.com");
            user.setName("test_" + baseName + "_" + suffix);
            user.setWebLink("https://example.com/" + baseName);
            user.setRole(Role.ROLE_USER);
            user.setConfirmationStatus(ConfirmationStatus.CONFIRMED);
            user.setInviteToken(inviteToken);

            users.add(user);
        }

        userRepository.saveAll(users);
        return new SeedResult(
                name(),
                "ok",
                users.size(),
                userRepository.count(),
                "created (3-5 users), login password for seeded users: " + DEFAULT_PASSWORD
        );
    }
}
