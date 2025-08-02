package br.com.gustavoakira.devconnect.application.domain.value_object;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    @Test
    void shouldCreateValidAddress() throws BusinessException {
        assertDoesNotThrow(()->new Address("Rua B", "Cidade Z", "Estado W", "CA", "98765-432"));

    }
    @Test
    void shouldThrownBusinessExceptionWithInvalidCountry(){
       final BusinessException exception = assertThrows(BusinessException.class,()->new Address("Rua B", "Cidade Z", "Estado W", "IN", "98765-432"));
       assertTrue(exception.getMessage().contains("Your Country is not in the accepted countries"));
    }
}