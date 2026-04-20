package de.upteams.tasktracker.user.controller.interfaces;

import de.upteams.tasktracker.user.dto.request.ForgotPasswordRequestDto;
import de.upteams.tasktracker.user.dto.request.PasswordResetRequestDto;
import de.upteams.tasktracker.user.dto.response.MessageResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PasswordResetApi {

    @PostMapping("/forgot-password")
    ResponseEntity<MessageResponseDto> forgotPassword(
            @Valid
            @RequestBody
            ForgotPasswordRequestDto request
    );

    @PostMapping("/reset-password")
    ResponseEntity<MessageResponseDto> resetPassword(
            @Valid
            @RequestBody
            PasswordResetRequestDto request
    );
}
