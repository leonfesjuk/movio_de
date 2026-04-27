package de.upteams.tasktracker.invitetoken.service;

import de.upteams.tasktracker.invitetoken.dto.response.InviteTokenResponse;
import de.upteams.tasktracker.invitetoken.entity.InviteToken;
import de.upteams.tasktracker.invitetoken.exception.InvalidInviteTokenException;
import de.upteams.tasktracker.invitetoken.persistence.InviteTokenRepository;
import de.upteams.tasktracker.invitetoken.service.impl.InviteTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InviteTokenServiceImplTest {

    private InviteTokenRepository inviteTokenRepository;
    private InviteTokenServiceImpl inviteTokenService;

    @BeforeEach
    void setUp() {
        inviteTokenRepository = mock(InviteTokenRepository.class);
        inviteTokenService = new InviteTokenServiceImpl(inviteTokenRepository);
    }

    // ------------------------------------------------------------------ //
    // generateTokens
    // ------------------------------------------------------------------ //

    @Test
    void shouldGenerateRequestedNumberOfTokens() {
        when(inviteTokenRepository.save(any(InviteToken.class)))
                .thenAnswer(invocation -> {
                    InviteToken token = invocation.getArgument(0);
                    token.setCreatedAt(Instant.now());
                    return token;
                });

        List<InviteTokenResponse> result = inviteTokenService.generateTokens(3);

        assertEquals(3, result.size());
        verify(inviteTokenRepository, times(3)).save(any(InviteToken.class));
    }

    @Test
    void shouldReturnTokensWithNonBlankValues() {
        when(inviteTokenRepository.save(any(InviteToken.class)))
                .thenAnswer(invocation -> {
                    InviteToken token = invocation.getArgument(0);
                    token.setCreatedAt(Instant.now());
                    return token;
                });

        List<InviteTokenResponse> result = inviteTokenService.generateTokens(2);

        result.forEach(response -> {
            assertNotNull(response.token());
            assertFalse(response.token().isBlank());
        });
    }

    @Test
    void shouldGenerateUniqueTokenValues() {
        when(inviteTokenRepository.save(any(InviteToken.class)))
                .thenAnswer(invocation -> {
                    InviteToken token = invocation.getArgument(0);
                    token.setCreatedAt(Instant.now());
                    return token;
                });

        List<InviteTokenResponse> result = inviteTokenService.generateTokens(10);

        long uniqueCount = result.stream()
                .map(InviteTokenResponse::token)
                .distinct()
                .count();

        assertEquals(10, uniqueCount);
    }

    @Test
    void shouldReturnTokensWithUsedFalseAndNullUsedAt() {
        when(inviteTokenRepository.save(any(InviteToken.class)))
                .thenAnswer(invocation -> {
                    InviteToken token = invocation.getArgument(0);
                    token.setCreatedAt(Instant.now());
                    return token;
                });

        List<InviteTokenResponse> result = inviteTokenService.generateTokens(1);

        InviteTokenResponse response = result.get(0);
        assertFalse(response.used());
        assertNull(response.usedAt());
    }

    @Test
    void shouldReturnTokensWithCreatedAtSet() {
        when(inviteTokenRepository.save(any(InviteToken.class)))
                .thenAnswer(invocation -> {
                    InviteToken token = invocation.getArgument(0);
                    token.setCreatedAt(Instant.now());
                    return token;
                });

        List<InviteTokenResponse> result = inviteTokenService.generateTokens(1);

        assertNotNull(result.get(0).createdAt());
    }

    // ------------------------------------------------------------------ //
    // getAllTokens
    // ------------------------------------------------------------------ //

    @Test
    void shouldReturnAllTokensMappedToResponse() {
        InviteToken first = buildToken("token-one", false);
        InviteToken second = buildToken("token-two", true);
        when(inviteTokenRepository.findAll()).thenReturn(List.of(first, second));

        List<InviteTokenResponse> result = inviteTokenService.getAllTokens();

        assertEquals(2, result.size());

        assertEquals("token-one", result.get(0).token());
        assertFalse(result.get(0).used());
        assertNull(result.get(0).usedAt());

        assertEquals("token-two", result.get(1).token());
        assertTrue(result.get(1).used());
        assertNotNull(result.get(1).usedAt());
    }

    @Test
    void shouldReturnEmptyListWhenNoTokensExist() {
        when(inviteTokenRepository.findAll()).thenReturn(List.of());

        List<InviteTokenResponse> result = inviteTokenService.getAllTokens();

        assertTrue(result.isEmpty());
    }

    // ------------------------------------------------------------------ //
    // validateToken
    // ------------------------------------------------------------------ //

    @Test
    void shouldReturnTokenWhenValidAndNotUsed() {
        InviteToken token = buildToken("valid-token", false);
        when(inviteTokenRepository.findByToken("valid-token")).thenReturn(Optional.of(token));

        InviteToken result = inviteTokenService.validateToken("valid-token");

        assertEquals(token, result);
    }

    @Test
    void shouldThrowWhenTokenNotFound() {
        when(inviteTokenRepository.findByToken("unknown")).thenReturn(Optional.empty());

        InvalidInviteTokenException ex = assertThrows(
                InvalidInviteTokenException.class,
                () -> inviteTokenService.validateToken("unknown")
        );

        assertNotNull(ex.getMessage());
    }

    @Test
    void shouldThrowWhenTokenIsAlreadyUsed() {
        InviteToken usedToken = buildToken("used-token", true);
        when(inviteTokenRepository.findByToken("used-token")).thenReturn(Optional.of(usedToken));

        assertThrows(
                InvalidInviteTokenException.class,
                () -> inviteTokenService.validateToken("used-token")
        );
    }

    // ------------------------------------------------------------------ //
    // markTokenAsUsed
    // ------------------------------------------------------------------ //

    @Test
    void shouldMarkTokenAsUsed() {
        InviteToken token = buildToken("active-token", false);
        when(inviteTokenRepository.findByToken("active-token")).thenReturn(Optional.of(token));
        when(inviteTokenRepository.save(token)).thenReturn(token);

        inviteTokenService.markTokenAsUsed("active-token");

        assertNotNull(token.getUsedAt());
        assertTrue(token.isUsed());
        verify(inviteTokenRepository).save(token);
    }

    @Test
    void shouldThrowWhenMarkingAlreadyUsedToken() {
        InviteToken usedToken = buildToken("used-token", true);
        when(inviteTokenRepository.findByToken("used-token")).thenReturn(Optional.of(usedToken));

        assertThrows(
                InvalidInviteTokenException.class,
                () -> inviteTokenService.markTokenAsUsed("used-token")
        );

        verify(inviteTokenRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenMarkingNonExistentToken() {
        when(inviteTokenRepository.findByToken("ghost-token")).thenReturn(Optional.empty());

        assertThrows(
                InvalidInviteTokenException.class,
                () -> inviteTokenService.markTokenAsUsed("ghost-token")
        );

        verify(inviteTokenRepository, never()).save(any());
    }

    // ------------------------------------------------------------------ //
    // helpers
    // ------------------------------------------------------------------ //

    private InviteToken buildToken(String tokenValue, boolean used) {
        InviteToken token = InviteToken.builder()
                .token(tokenValue)
                .build();
        token.setCreatedAt(Instant.now());
        if (used) {
            token.setUsedAt(Instant.now());
        }
        return token;
    }
}