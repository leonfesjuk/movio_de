package de.upteams.tasktracker.invitetoken.entity;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class InviteTokenTest {

    @Test
    void shouldReturnFalseWhenUsedAtIsNull() {
        InviteToken token = InviteToken.builder()
                .token("some-token")
                .build();

        assertFalse(token.isUsed());
    }

    @Test
    void shouldReturnTrueWhenUsedAtIsSet() {
        InviteToken token = InviteToken.builder()
                .token("some-token")
                .build();

        token.setUsedAt(Instant.now());

        assertTrue(token.isUsed());
    }

    @Test
    void shouldSetCreatedAtOnPrePersist() {
        InviteToken token = InviteToken.builder()
                .token("some-token")
                .build();

        assertNull(token.getCreatedAt());

        token.onCreate();

        assertNotNull(token.getCreatedAt());
    }

    @Test
    void shouldNotOverwriteCreatedAtIfCalledTwice() {
        InviteToken token = InviteToken.builder()
                .token("some-token")
                .build();

        token.onCreate();
        Instant first = token.getCreatedAt();

        token.onCreate();
        Instant second = token.getCreatedAt();

        assertNotNull(second);
        assertFalse(second.isBefore(first));
    }

    @Test
    void shouldStoreTokenValue() {
        String value = "abc123xyz";

        InviteToken token = InviteToken.builder()
                .token(value)
                .build();

        assertEquals(value, token.getToken());
    }
}