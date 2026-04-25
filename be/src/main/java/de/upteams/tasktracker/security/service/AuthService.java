package de.upteams.tasktracker.security.service;

import de.upteams.tasktracker.exception.handling.exceptions.common.RestApiException;
import de.upteams.tasktracker.security.dto.LoginRequest;
import de.upteams.tasktracker.security.entities.TokenResponseDto;
import de.upteams.tasktracker.user.dto.response.UserResponseDto;
import de.upteams.tasktracker.user.entity.AppUser;
import de.upteams.tasktracker.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public TokenResponseDto login(LoginRequest loginRequest) {
        String userEmail = loginRequest.email();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails, loginRequest.password())
            );
        } catch (DisabledException ex) {
            log.warn("Login attempt for inactive account: {}", userEmail, ex);
            throw new RestApiException(
                    HttpStatus.FORBIDDEN,
                    "User account is not active. Please confirm your email."
            );
        } catch (LockedException ex) {
            log.warn("Login attempt for locked account: {}", userEmail, ex);
            throw new RestApiException(
                    HttpStatus.FORBIDDEN,
                    "User account is locked."
            );
        } catch (BadCredentialsException ex) {
            log.warn("Bad credentials for user: {}", userEmail, ex);
            throw new RestApiException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password."
            );
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenService.generateAccessToken(userEmail);
        String refreshToken = jwtTokenService.generateRefreshToken(userEmail);
        return new TokenResponseDto(accessToken, refreshToken);
    }


    public String refreshAccessToken(String refreshToken) {
        if (jwtTokenService.validateToken(refreshToken, JwtTokenService.TokenType.REFRESH)) {
            String username = jwtTokenService.getUsernameFromToken(refreshToken, JwtTokenService.TokenType.REFRESH);
            return jwtTokenService.generateAccessToken(username);
        }
        throw new RestApiException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }

    public UserResponseDto getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        AppUser user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDto(
                user.getEmail(),
                user.getRole().name(),
                user.getConfirmationStatus()
        );
    }
}
