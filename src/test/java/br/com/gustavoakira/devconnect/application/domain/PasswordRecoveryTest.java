package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PasswordRecoveryTest {

    private final Instant now = Instant.now();
    private final Instant future = now.plusSeconds(600);
    private final Instant past = now.minusSeconds(600);

    @Test
    void shouldCreatePasswordRecoverySuccessfully() {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, future);

        assertNotNull(recovery);
        assertEquals(1L, recovery.getId());
        assertEquals("token123", recovery.getToken());
        assertEquals(10L, recovery.getUserId());
        assertEquals(future, recovery.getExpiresAt());
        assertNull(recovery.getUsedAt());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PasswordRecovery(1L, null, 10L, future));
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PasswordRecovery(1L, "token123", null, future));
    }

    @Test
    void shouldThrowExceptionWhenExpiresAtIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PasswordRecovery(1L, "token123", 10L, null));
    }

    @Test
    void shouldReturnTrueWhenExpired() {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, past);

        assertTrue(recovery.isExpired(now));
    }

    @Test
    void shouldReturnFalseWhenNotExpired() {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, future);

        assertFalse(recovery.isExpired(now));
    }

    @Test
    void shouldReturnFalseWhenNotUsed() {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, future);

        assertFalse(recovery.isUsed());
    }

    @Test
    void shouldReturnTrueWhenUsed() throws BusinessException {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, future);

        recovery.markAsUsed(now);

        assertTrue(recovery.isUsed());
        assertNotNull(recovery.getUsedAt());
    }

    @Test
    void shouldReturnValidWhenNotExpiredAndNotUsed() {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, future);

        assertTrue(recovery.isValid(now));
    }

    @Test
    void shouldReturnInvalidWhenExpired() {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, past);

        assertFalse(recovery.isValid(now));
    }

    @Test
    void shouldReturnInvalidWhenUsed() throws BusinessException {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, future);

        recovery.markAsUsed(now);

        assertFalse(recovery.isValid(now));
    }

    @Test
    void shouldMarkAsUsedSuccessfully() throws BusinessException {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, future);

        recovery.markAsUsed(now);

        assertNotNull(recovery.getUsedAt());
        assertEquals(now, recovery.getUsedAt());
    }

    @Test
    void shouldThrowExceptionWhenMarkAsUsedTwice() throws BusinessException {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, future);

        recovery.markAsUsed(now);

        final BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> recovery.markAsUsed(now));

        assertEquals("Token already used", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMarkAsUsedExpiredToken() {
        final PasswordRecovery recovery =
                new PasswordRecovery(1L, "token123", 10L, past);

        final BusinessException exception =
                assertThrows(BusinessException.class,
                        () -> recovery.markAsUsed(now));

        assertEquals("Token expired", exception.getMessage());
    }
}
