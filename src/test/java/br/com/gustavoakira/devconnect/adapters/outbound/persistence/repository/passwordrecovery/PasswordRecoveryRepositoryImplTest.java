package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.passwordrecovery;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.PasswordRecoveryEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers.PasswordRecoveryMapper;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordRecoveryRepositoryImplTest {

    @Mock
    private SpringDataPostgresPasswordRecoveryRepository repository;

    @Mock
    private PasswordRecoveryMapper mapper;

    @InjectMocks
    private PasswordRecoveryRepositoryImpl recoveryRepository;

    private PasswordRecovery domain;
    private PasswordRecoveryEntity entity;

    @BeforeEach
    void setup() {
        domain = new PasswordRecovery(null,"token",2L, Instant.now());
        entity = new PasswordRecoveryEntity();
    }

    @Test
    void shouldSavePasswordRecovery() throws Exception {
        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        final PasswordRecovery result = recoveryRepository.save(domain);

        assertEquals(domain, result);
        verify(repository).save(entity);
        verify(mapper).toEntity(domain);
        verify(mapper).toDomain(entity);
    }

    @Test
    void shouldFindByToken() throws Exception {
        final String token = "token123";

        when(repository.findByToken(token)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        final PasswordRecovery result = recoveryRepository.findByToken(token);

        assertEquals(domain, result);
        verify(repository).findByToken(token);
        verify(mapper).toDomain(entity);
    }

    @Test
    void shouldInvalidateAllByUserId() {
        final Long userId = 1L;

        recoveryRepository.invalidateAllByUserId(userId);

        verify(repository).deleteAllByUserId(userId);
    }
}
