package de.upteams.tasktracker.user.service;

import de.upteams.tasktracker.user.dto.request.ProfileUpdateDto;
import de.upteams.tasktracker.user.dto.response.UserResponseDto;

public interface UserProfileService {

    UserResponseDto getUserProfile(String userId);

    UserResponseDto updateProfile(String userId, ProfileUpdateDto updateDto);

//    void changePassword(String userId, PasswordChangeDto dto)
}
