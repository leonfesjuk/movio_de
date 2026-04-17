package de.upteams.tasktracker.user.controller.impl;

import de.upteams.tasktracker.user.controller.interfaces.PasswordResetApi;
import de.upteams.tasktracker.user.dto.request.ForgotPasswordRequestDto;
import de.upteams.tasktracker.user.dto.request.PasswordResetRequestDto;
import de.upteams.tasktracker.user.dto.response.MessageResponseDto;
import de.upteams.tasktracker.user.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PasswordResetControllerImpl implements PasswordResetApi {

    private final PasswordResetService passwordResetService;

    @Override
    public ResponseEntity<MessageResponseDto> forgotPassword(
            ForgotPasswordRequestDto request
    ) {
        passwordResetService.createResetToken(request.email());

        return ResponseEntity.ok(
                new MessageResponseDto("If account exists, password reset email was sent")
        );
    }

    @Override
    public ResponseEntity<MessageResponseDto> resetPassword(
            PasswordResetRequestDto request
    ) {
        passwordResetService.resetPassword(
                request.token(),
                request.newPassword()
        );

        return ResponseEntity.ok(
                new MessageResponseDto("Password successfully reset")
        );
    }
}
