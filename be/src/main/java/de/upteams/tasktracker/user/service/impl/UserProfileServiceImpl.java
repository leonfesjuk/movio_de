package de.upteams.tasktracker.user.service.impl;

import de.upteams.tasktracker.user.dto.request.ProfileUpdateDto;
import de.upteams.tasktracker.user.dto.response.UserResponseDto;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.service.UserProfileService;
import de.upteams.tasktracker.user.service.UserService;
import de.upteams.tasktracker.user.util.AppUserMapper;
import lombok.RequiredArgsConstructor;
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

//    @Override
//    @Transactional
//    public void changePassword(String userId, PasswordChangeDto dto) {
//        AppUser user = userService.getByIdOrThrow(userId);
//
//        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
//            throw new InvalidPasswordException("Invalid current password");
//        }
//
//        if (passwordEncoder.matches(dto.newPassword(), user.getPassword())) {
//            throw new InvalidPasswordException("New password must be different from current password");
//        }
//
//        user.setPassword(passwordEncoder.encode(dto.newPassword()));
//        userService.saveOrUpdate(user);
//    }
}