package de.upteams.tasktracker.user.controller.impl;

import de.upteams.tasktracker.security.service.AuthUserDetails;
import de.upteams.tasktracker.user.controller.interfaces.UserProfileApi;
import de.upteams.tasktracker.user.dto.request.PasswordChangeDto;
import de.upteams.tasktracker.user.dto.request.ProfileUpdateDto;
import de.upteams.tasktracker.user.dto.response.MessageResponseDto;
import de.upteams.tasktracker.user.dto.response.UserResponseDto;
import de.upteams.tasktracker.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserProfileControllerImpl implements UserProfileApi {

    private final UserProfileService profileService;

    @Override
    public UserResponseDto getCurrentUserProfile(
            @AuthenticationPrincipal AuthUserDetails principal
    ) {
        return profileService.getUserProfile(principal.user().getId().toString());
    }

    @Override
    public UserResponseDto getUserProfile(
            @PathVariable String userId
    ) {
        return profileService.getUserProfile(userId);
    }

    @Override
    public UserResponseDto updateProfile(
            @AuthenticationPrincipal AuthUserDetails principal,
            @RequestBody @Valid ProfileUpdateDto updateDto
    ) {
        return profileService.updateProfile(principal.user().getId().toString(), updateDto);
    }

    @Override
    public MessageResponseDto changePassword(PasswordChangeDto requestDto) {
        profileService.changePassword(requestDto);
        return new MessageResponseDto("Password changed successfully");
    }
}