package de.upteams.tasktracker.user.service.impl;

import de.upteams.tasktracker.security.service.AuthUserDetails;
import de.upteams.tasktracker.user.dto.request.PasswordChangeDto;
import de.upteams.tasktracker.user.dto.request.ProfileUpdateDto;
import de.upteams.tasktracker.user.dto.response.UserResponseDto;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.service.UserProfileService;
import de.upteams.tasktracker.user.service.UserService;
import de.upteams.tasktracker.user.util.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserService userService;
    private final AppUserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserProfile(String userId) {
        AppUser user = userService.getByIdOrThrow(userId);
        return userMapper.mapEntityToDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateProfile(String userId, ProfileUpdateDto updateDto) {
        AppUser user = userService.getByIdOrThrow(userId);

        user.updateProfile(
                updateDto.webLink()
        );

        userService.saveOrUpdate(user);

        return userMapper.mapEntityToDto(user);
    }

    @Override
    @Transactional
    public void changePassword(PasswordChangeDto dto) {
        AppUser user = getCurrentUser();

        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        if (!dto.newPassword().equals(dto.confirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (passwordEncoder.matches(dto.newPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from old password");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        userService.saveOrUpdate(user);
    }

    private AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();

        return userDetails.user();
    }
}