package de.upteams.tasktracker.invitetoken.service.impl;

import de.upteams.tasktracker.invitetoken.dto.response.InviteTokenResponse;
import de.upteams.tasktracker.invitetoken.entity.InviteToken;
import de.upteams.tasktracker.invitetoken.exception.InvalidInviteTokenException;
import de.upteams.tasktracker.invitetoken.persistence.InviteTokenRepository;
import de.upteams.tasktracker.invitetoken.service.interfaces.InviteTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteTokenServiceImpl implements InviteTokenService {

    private static final int TOKEN_BYTE_LENGTH = 32;

    private final InviteTokenRepository inviteTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<InviteTokenResponse> generateTokens(int count) {
        List<InviteTokenResponse> result = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            String tokenValue = generateSecureToken();

            InviteToken token = InviteToken.builder()
                    .token(tokenValue)
                    .build();

            InviteToken saved = inviteTokenRepository.save(token);

            result.add(new InviteTokenResponse(
                    saved.getToken(),
                    saved.getCreatedAt(),
                    null,
                    false
            ));
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InviteTokenResponse> getAllTokens() {
        return inviteTokenRepository.findAll().stream()
                .map(t -> new InviteTokenResponse(
                        t.getToken(),
                        t.getCreatedAt(),
                        t.getUsedAt(),
                        t.isUsed()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InviteToken validateToken(String rawToken) {
        return inviteTokenRepository.findAll().stream()
                .filter(t -> !t.isUsed())
                .filter(t -> passwordEncoder.matches(rawToken, t.getToken()))
                .findFirst()
                .orElseThrow(() -> new InvalidInviteTokenException("Invite token is invalid or has already been used"));
    }

    @Override
    @Transactional
    public void markTokenAsUsed(String rawToken) {
        InviteToken token = validateToken(rawToken);

        if (token.isUsed()) {
            throw new InvalidInviteTokenException("Token has already been used");
        }

        token.setUsedAt(Instant.now());
        inviteTokenRepository.save(token);
    }

    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[TOKEN_BYTE_LENGTH];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
