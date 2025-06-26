package br.com.gustavoakira.devconnect.application.domain.value_object;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test
    void shouldCreateValidPassword() throws BusinessException {
        String valid = "Str0ng@Pass";
        Password password = new Password(valid);
        assertEquals(valid, password.getValue());
    }

    @Test
    void shouldThrowExceptionForShortPassword() {
        Exception exception = assertThrows(BusinessException.class, () -> {
            new Password("Sh0rt!");
        });
        assertTrue(exception.getMessage().contains("curta"));
    }

    @Test
    void shouldThrowExceptionWhenNoUppercase() {
        Exception exception = assertThrows(BusinessException.class, () -> {
            new Password("weak@123");
        });
        assertTrue(exception.getMessage().toLowerCase().contains("maiúscula"));
    }

    @Test
    void shouldThrowExceptionWhenNoLowercase() {
        Exception exception = assertThrows(BusinessException.class, () -> {
            new Password("WEAK@123");
        });
        assertTrue(exception.getMessage().toLowerCase().contains("minúscula"));
    }

    @Test
    void shouldThrowExceptionWhenNoNumber() {
        Exception exception = assertThrows(BusinessException.class, () -> {
            new Password("Weak@Pwd");
        });
        assertTrue(exception.getMessage().toLowerCase().contains("número"));
    }

    @Test
    void shouldThrowExceptionWhenNoSpecialChar() {
        Exception exception = assertThrows(BusinessException.class, () -> {
            new Password("Weak1234");
        });
        assertTrue(exception.getMessage().toLowerCase().contains("especial"));
    }

}