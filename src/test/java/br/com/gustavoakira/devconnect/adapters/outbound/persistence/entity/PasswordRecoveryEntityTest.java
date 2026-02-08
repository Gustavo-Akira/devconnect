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

    @Test
    void shouldCreateAnCompletePasswordRecoveryEntity(){
        final PasswordRecovery domain = new PasswordRecovery(1L,"sdfdfdfsgfdss",2L, Instant.now());
        final PasswordRecoveryEntity entity = new PasswordRecoveryEntity(domain);
        assertEquals(domain.getId(),entity.getId());
        assertEquals(domain.getUserId(), entity.getUserId());
        assertEquals(domain.getExpiresAt(), entity.getExpiresAt());
    }
}