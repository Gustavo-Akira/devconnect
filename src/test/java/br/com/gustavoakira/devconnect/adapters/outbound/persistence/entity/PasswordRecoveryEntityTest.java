package br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity;

import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PasswordRecoveryEntityTest {
    @Test
    void shouldCreateAnEmptyPasswordRecoveryEntity(){
        assertDoesNotThrow(()->new PasswordRecoveryEntity());
    }
}