package br.com.gustavoakira.devconnect.application.services.passwordrecovery;

import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IPasswordRecoveryRepository;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.command.CompletePasswordRecoveryCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompletePasswordRecoveryUseCaseImplTest {

    private IPasswordRecoveryRepository recoveryRepository;
    private IUserRepository userRepository;

    private CompletePasswordRecoveryUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        recoveryRepository = Mockito.mock(IPasswordRecoveryRepository.class);
        userRepository = Mockito.mock(IUserRepository.class);
        useCase = new CompletePasswordRecoveryUseCaseImpl(recoveryRepository, userRepository);
    }

    private PasswordRecovery createValidRecovery() {
        return new PasswordRecovery(
                1L,
                "token123",
                10L,
                Instant.now().plusSeconds(300)
        );
    }

    private User createUser() throws BusinessException {
        return new User(
                10L,
                "oldPassword",
                "gustavo@email.com",
                true
        );
    }

    @Test
    void shouldCompletePasswordRecoverySuccessfully() throws Exception {

        final PasswordRecovery recovery = createValidRecovery();
        final User user = createUser();

        when(recoveryRepository.findByToken("token123")).thenReturn(recovery);
        when(userRepository.findById(10L)).thenReturn(user);
        when(recoveryRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        final CompletePasswordRecoveryCommand command =
                new CompletePasswordRecoveryCommand( "newPassword","token123");

        final PasswordRecovery result = useCase.execute(command);

        assertNotNull(result);
        assertTrue(result.isUsed());

        verify(userRepository).save(user);
        verify(recoveryRepository).save(recovery);
    }

    @Test
    void shouldThrowWhenTokenAlreadyUsed() throws Exception {

        final PasswordRecovery recovery = createValidRecovery();
        recovery.markAsUsed(Instant.now());

        when(recoveryRepository.findByToken("token123")).thenReturn(recovery);
        when(userRepository.findById(any())).thenReturn(createUser());
        final CompletePasswordRecoveryCommand command =
                new CompletePasswordRecoveryCommand("newPassword","token123");

        assertThrows(BusinessException.class,
                () -> useCase.execute(command));

        verify(userRepository, never()).save(any());
        verify(recoveryRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenTokenExpired() throws Exception {

        final PasswordRecovery recovery = new PasswordRecovery(
                1L,
                "token123",
                10L,
                Instant.now().minusSeconds(10)
        );

        when(recoveryRepository.findByToken("token123")).thenReturn(recovery);
        when(userRepository.findById(10L)).thenReturn(createUser());

        final CompletePasswordRecoveryCommand command =
                new CompletePasswordRecoveryCommand( "newPassword","token123");

        assertThrows(BusinessException.class,
                () -> useCase.execute(command));

        verify(userRepository, never()).save(any());
        verify(recoveryRepository, never()).save(any());
    }
}
