package de.upteams.tasktracker.user.controller.interfaces;

import de.upteams.tasktracker.security.service.AuthUserDetails;
import de.upteams.tasktracker.user.dto.request.ProfileUpdateDto;
import de.upteams.tasktracker.user.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users/profile")
@Tag(name = "User Profile", description = "Endpoints for managing user profiles")
public interface UserProfileApi {

    @Operation(summary = "Get current user profile")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    UserResponseDto getCurrentUserProfile(
            @AuthenticationPrincipal AuthUserDetails principal
    );

    @Operation(summary = "Get user profile by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    UserResponseDto getUserProfile(
            @PathVariable String userId
    );

    @Operation(summary = "Update current user profile")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me")
    UserResponseDto updateProfile(
            @AuthenticationPrincipal AuthUserDetails principal,
            @RequestBody @Valid ProfileUpdateDto updateDto
    );
}
