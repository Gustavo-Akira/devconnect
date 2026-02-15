package br.com.gustavoakira.devconnect.application.services.passwordrecovery;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IPasswordRecoveryRepository;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.command.RequestPasswordRecoveryCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestPasswordRecoveryUseCaseImplTest {

    private IPasswordRecoveryRepository passwordRecoveryRepository;
    private IUserRepository userRepository;

    private RequestPasswordRecoveryUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        passwordRecoveryRepository = mock(IPasswordRecoveryRepository.class);
        userRepository = mock(IUserRepository.class);

        useCase = new RequestPasswordRecoveryUseCaseImpl(passwordRecoveryRepository, userRepository);
    }

    @Test
    void shouldCreatePasswordRecoveryWhenUserExists() throws BusinessException, EntityNotFoundException {
        final String email = "test@email.com";
        final Long userId = 1L;

        final User user = new User(userId,"password",email,true);
        when(userRepository.findByEmail(email)).thenReturn(user);

        final RequestPasswordRecoveryCommand command =
                new RequestPasswordRecoveryCommand(email);

        useCase.execute(command);

        verify(passwordRecoveryRepository).invalidateAllByUserId(userId);

        final ArgumentCaptor<PasswordRecovery> captor =
                ArgumentCaptor.forClass(PasswordRecovery.class);

        verify(passwordRecoveryRepository).save(captor.capture());

        final PasswordRecovery saved = captor.getValue();

        assertNotNull(saved.getToken());
        assertEquals(userId, saved.getUserId());
        assertTrue(saved.getExpiresAt().isAfter(Instant.now()));
    }

    @Test
    void shouldNotThrowWhenUserNotFound() throws BusinessException, EntityNotFoundException {
        final String email = "notfound@email.com";

        when(userRepository.findByEmail(email))
                .thenThrow(new EntityNotFoundException("User not found"));

        final RequestPasswordRecoveryCommand command =
                new RequestPasswordRecoveryCommand(email);

        assertDoesNotThrow(() -> useCase.execute(command));

        verify(passwordRecoveryRepository, never()).save(any());
        verify(passwordRecoveryRepository, never()).invalidateAllByUserId(any());
    }

    @Test
    void shouldNotPropagateBusinessException() throws BusinessException, EntityNotFoundException {
        final String email = "test@email.com";
        final Long userId = 1L;

        final User user = new User(userId, "password", email, true);
        when(userRepository.findByEmail(email)).thenReturn(user);

        doThrow(new BusinessException("DB error"))
                .when(passwordRecoveryRepository).save(any());

        final RequestPasswordRecoveryCommand command =
                new RequestPasswordRecoveryCommand(email);

        assertDoesNotThrow(() -> useCase.execute(command));
    }
}
