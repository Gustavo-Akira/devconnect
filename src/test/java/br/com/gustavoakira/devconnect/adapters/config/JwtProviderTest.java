package br.com.gustavoakira.devconnect.adapters.config;

import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private JwtProvider jwtProvider;

    private static final String SECRET =
            "my-super-secret-key-for-jwt-tests-my-super-secret-key";

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(SECRET);
    }

    @Test
    void shouldGenerateValidToken() throws BusinessException {
        final User user = new User(
                1L,
                "Gustavo Akira",
                "password123",
                "gustavo@email.com",
                true
        );

        final String token = jwtProvider.generateToken(user, 1000 * 60);

        assertNotNull(token);
        assertTrue(jwtProvider.isValid(token));
    }

    @Test
    void shouldExtractSubjectFromToken() throws BusinessException {
        final User user = new User(
                42L,
                "Gustavo Akira",
                "password123",
                "gustavo@email.com",
                true
        );

        final String token = jwtProvider.generateToken(user, 1000 * 60);

        final String subject = jwtProvider.getSubject(token);

        assertEquals("42", subject);
    }

    @Test
    void shouldInvalidateExpiredToken() throws BusinessException, InterruptedException {
        final User user = new User(
                1L,
                "Gustavo Akira",
                "password123",
                "gustavo@email.com",
                true
        );

        final String token = jwtProvider.generateToken(user, 1); // 1ms
        Thread.sleep(5);

        assertFalse(jwtProvider.isValid(token));
    }

    @Test
    void shouldInvalidateMalformedToken() {
        final String invalidToken = "this.is.not.a.jwt";

        assertFalse(jwtProvider.isValid(invalidToken));
    }

    @Test
    void shouldGenerateTokenWithoutNameClaim() throws BusinessException {
        final User user = new User(
                1L,
                "Gustavo Akira",
                "password123",
                "gustavo@email.com",
                true
        );

        final String token = jwtProvider.generateToken(user, 1000 * 60);

        assertTrue(jwtProvider.isValid(token));
        assertEquals("1", jwtProvider.getSubject(token));
    }
}
