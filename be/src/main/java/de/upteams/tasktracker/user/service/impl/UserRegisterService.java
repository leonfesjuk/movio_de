package de.upteams.tasktracker.user.service.impl;

import de.upteams.tasktracker.exception.handling.exceptions.common.FieldValidationException;
import de.upteams.tasktracker.invitetoken.entity.InviteToken;
import de.upteams.tasktracker.invitetoken.exception.InvalidInviteTokenException;
import de.upteams.tasktracker.invitetoken.persistence.InviteTokenRepository;
import de.upteams.tasktracker.mail.EmailService;
import de.upteams.tasktracker.mail.confirmation.code.ConfirmationCode;
import de.upteams.tasktracker.mail.confirmation.code.interfaces.ConfirmationService;
import de.upteams.tasktracker.user.dto.request.UserCreateDto;
import de.upteams.tasktracker.user.dto.response.UserCreateResponseDto;
import de.upteams.tasktracker.user.dto.response.UserResponseDto;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.exception.UserAlreadyExistException;
import de.upteams.tasktracker.user.persistence.UserRepository;
import de.upteams.tasktracker.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static de.upteams.tasktracker.user.entity.ConfirmationStatus.CONFIRMED;
import static de.upteams.tasktracker.user.entity.ConfirmationStatus.UNCONFIRMED;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationService confirmationService;
    private final UserService userService;
    private final InviteTokenRepository inviteTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserCreateResponseDto register(final UserCreateDto dto) {
        final String normalizedInviteToken = dto.inviteToken().trim();
        final InviteToken inviteToken = inviteTokenRepository
                .findByToken(normalizedInviteToken)
                .orElseThrow(() -> new InvalidInviteTokenException("Invalid invite token"));
        final String normalizedEmail = dto.email().toLowerCase().trim();
        final String encodedPassword = passwordEncoder.encode(dto.password());
        final String normalizedName = dto.name().trim();
        final String normalizedWebLink = normalizeWebLink(dto.webLink());

        if (inviteToken.isUsed()) {
            throw new InvalidInviteTokenException("Invite token already used");
        }

        if (userService.existsByName(normalizedName)) {
            throw new FieldValidationException("name", "Name already exists");
        }

        final Optional<AppUser> foundUserByEmail = userService.getByEmail(normalizedEmail);
        if (foundUserByEmail.isPresent()) {
            return handleExistingUser(foundUserByEmail.get());
        }

        final AppUser appUser = new AppUser(
                encodedPassword,
                normalizedEmail,
                dto.name(),
                normalizedWebLink);
        appUser.setInviteToken(inviteToken);
        final AppUser savedNewUser = userService.saveOrUpdate(appUser);

        String confirmationCode = confirmationService.generateConfirmationCode(savedNewUser);
        emailService.sendConfirmationEmail(savedNewUser.getEmail(), confirmationCode);

        return new UserCreateResponseDto(
                savedNewUser.getId().toString(),
                savedNewUser.getEmail(),
                savedNewUser.getRole().name(),
                false
        );
    }

    private UserCreateResponseDto handleExistingUser(AppUser existingUser) {
        if (UNCONFIRMED.equals(existingUser.getConfirmationStatus())) {
            String confirmationCode = confirmationService.regenerateCode(existingUser);
            emailService.sendConfirmationEmail(existingUser.getEmail(), confirmationCode);
            return new UserCreateResponseDto(
                    existingUser.getId().toString(),
                    existingUser.getEmail(),
                    existingUser.getRole().name(),
                    true);
        }
        throw new FieldValidationException("email", "Email already exists");
    }

    @Transactional
    public UserResponseDto confirmRegistration(final String code) {
        final ConfirmationCode confirmationToken = confirmationService.getConfirmationIfValidOrThrow(code);

        final AppUser registeredUser = confirmationToken.getUser();

        registeredUser.setConfirmationStatus(CONFIRMED);
        userService.saveOrUpdate(registeredUser);

        InviteToken inviteToken = registeredUser.getInviteToken();
        inviteToken.setUsedAt(Instant.now());
        inviteTokenRepository.save(inviteToken);

        List<AppUser> competitors = userRepository
                .findAllByInviteTokenAndNotId(inviteToken, registeredUser.getId());

        userRepository.deleteAll(competitors);

        confirmationService.removeToken(confirmationToken);

        return new UserResponseDto(
                registeredUser.getEmail(),
                registeredUser.getRole().name(),
                registeredUser.getConfirmationStatus()
        );
    }

    private String normalizeWebLink(String webLink) {
        String trimmed = webLink.trim();

        if (!trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
            return "https://" + trimmed;
        }

        return trimmed;
    }
}
