package br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.PasswordRecoveryEntity;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PasswordRecoveryMapperTest {

    private PasswordRecoveryMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new PasswordRecoveryMapper();
    }

    @Test
    void shouldMapDomainToEntity() {
        Instant expiresAt = Instant.now().plusSeconds(3600);

        PasswordRecovery domain = new PasswordRecovery(
                1L,
                "token-123",
                10L,
                expiresAt
        );

        PasswordRecoveryEntity entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getToken(), entity.getToken());
        assertEquals(domain.getUserId(), entity.getUserId());
        assertEquals(domain.getExpiresAt(), entity.getExpiresAt());
        assertNull(entity.getUsedAt());
    }

    @Test
    void shouldMapEntityToDomain_whenNotUsed() throws BusinessException {
        Instant expiresAt = Instant.now().plusSeconds(3600);

        PasswordRecoveryEntity entity = new PasswordRecoveryEntity();
        entity.setId(2L);
        entity.setToken("token-abc");
        entity.setUserId(20L);
        entity.setExpiresAt(expiresAt);
        entity.setUsedAt(null);

        PasswordRecovery domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getToken(), domain.getToken());
        assertEquals(entity.getUserId(), domain.getUserId());
        assertEquals(entity.getExpiresAt(), domain.getExpiresAt());
        assertFalse(domain.isUsed());
    }

    @Test
    void shouldMapEntityToDomain_whenUsed() throws BusinessException {
        Instant expiresAt = Instant.now().plusSeconds(3600);
        Instant usedAt = Instant.now();

        PasswordRecoveryEntity entity = new PasswordRecoveryEntity();
        entity.setId(3L);
        entity.setToken("token-used");
        entity.setUserId(30L);
        entity.setExpiresAt(expiresAt);
        entity.setUsedAt(usedAt);

        PasswordRecovery domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertTrue(domain.isUsed());
        assertEquals(usedAt, domain.getUsedAt());
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() throws BusinessException {
        PasswordRecovery result = mapper.toDomain(null);
        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenDomainIsNull() {
        PasswordRecoveryEntity result = mapper.toEntity(null);
        assertNull(result);
    }
}
