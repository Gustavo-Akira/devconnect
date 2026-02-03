package br.com.gustavoakira.devconnect.application.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

class UserTest {

    @Test
    void shouldCreateUserWithValidData() throws BusinessException {
        final User user = new User(
                "StrongPassword123",
                "gustavo@email.com",
                true
        );

        assertNotNull(user);
        assertEquals("gustavo@email.com", user.getEmail());
        assertTrue(user.isActive());
        assertNotNull(user.getPassword());
    }

    @Test
    void shouldCreateUserWithValidDataButWithoutName() throws BusinessException {
        final User user = new User(
                "StrongPassword123",
                "gustavo@email.com",
                true
        );

        assertNotNull(user);
        assertEquals("gustavo@email.com", user.getEmail());
        assertTrue(user.isActive());
        assertNotNull(user.getPassword());
    }

    @Test
    void shouldCreateUserWithValidDataButWithoutNameWithId() throws BusinessException {
        final User user = new User(
                1L,
                "StrongPassword123",
                "gustavo@email.com",
                true
        );

        assertNotNull(user);
        assertEquals("gustavo@email.com", user.getEmail());
        assertTrue(user.isActive());
        assertNotNull(user.getPassword());
    }

    @Test
    void shouldCreateUserAsActiveWhenIsActiveIsNull() throws BusinessException {
        final User user = new User(
                "StrongPassword123",
                "gustavo@email.com",
                null
        );

        assertTrue(user.isActive());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> new User(
                        "StrongPassword123",
                        "invalid-email",
                        true
                )
        );

        assertEquals("Should contain an @ in emaill", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameHasOnlyOneWord() {
        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> new User(
                        "StrongPassword123",
                        "gustavo",
                        true
                )
        );

        assertEquals("Should contain an @ in emaill", exception.getMessage());
    }

    @Test
    void shouldDisableUser() throws BusinessException {
        final User user = new User(
                "StrongPassword123",
                "gustavo@email.com",
                true
        );

        user.disable();

        assertFalse(user.isActive());
    }

    @Test
    void shouldChangePassword() throws BusinessException {
        final User user = new User(
                "OldPassword123",
                "gustavo@email.com",
                true
        );

        user.changePassword("NewPassword456");

        assertEquals("NewPassword456", user.getPassword().getValue());
    }

    @Test
    void shouldSetIdAfterCreation() throws BusinessException {
        final User user = new User(
                "StrongPassword123",
                "gustavo@email.com",
                true
        );

        user.setId(10L);

        assertEquals(10L, user.getId());
    }

    @Test
    void shouldCreateUserWithIdConstructor() throws BusinessException {
        final User user = new User(
                1L,
                "StrongPassword123",
                "gustavo@email.com",
                true
        );

        assertEquals(1L, user.getId());
        assertEquals("gustavo@email.com", user.getEmail());
        assertTrue(user.isActive());
    }
}

