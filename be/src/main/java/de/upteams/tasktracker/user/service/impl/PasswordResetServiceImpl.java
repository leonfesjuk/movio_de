package de.upteams.tasktracker.user.service.impl;

import de.upteams.tasktracker.mail.EmailService;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.entity.PasswordResetToken;
import de.upteams.tasktracker.user.exception.InvalidTokenException;
import de.upteams.tasktracker.user.persistence.PasswordResetTokenRepository;
import de.upteams.tasktracker.user.persistence.UserRepository;
import de.upteams.tasktracker.user.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private static final int TOKEN_EXPIRATION_MINUTES = 30;

    @Override
    @Transactional
    public void createResetToken(String email) {

        userRepository.findByEmailIgnoreCase(email).ifPresent(user -> {
            invalidateOldTokens(user);

            String token = UUID.randomUUID().toString();

            PasswordResetToken resetToken = PasswordResetToken.builder()
                    .token(token)
                    .user(user)
                    .expiryDate(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES))
                    .used(false)
                    .build();

            tokenRepository.save(resetToken);

            emailService.sendResetPasswordEmail(user.getEmail(), token);
        });
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);

        validateToken(resetToken);

        AppUser user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

    private void invalidateOldTokens(AppUser user) {
        List<PasswordResetToken> tokens =
                tokenRepository.findByUserAndUsedFalse(user);

        tokens.forEach(t -> t.setUsed(true));
        tokenRepository.saveAll(tokens);
    }

    private void validateToken(PasswordResetToken token) {
        if (token.isUsed()) {
            throw new InvalidTokenException();
        }

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException();
        }
    }
}
