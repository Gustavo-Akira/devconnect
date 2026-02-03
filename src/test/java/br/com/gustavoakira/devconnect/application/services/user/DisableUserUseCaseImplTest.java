package br.com.gustavoakira.devconnect.application.services.user;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.ForbiddenException;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.user.command.DisableUserCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DisableUserUseCaseImplTest {

    @Mock
    private IUserRepository repository;

    @InjectMocks
    private DisableUserUseCaseImpl useCase;

    private User user;

    @BeforeEach
    void setup() throws BusinessException {
        user = new User(
                1L,
                "encoded-password",
                "gustavo@email.com",
                true
        );
    }

    @Test
    void shouldDisableUserWhenUserIsAuthorized() throws Exception {
        final Long userId = 1L;
        final DisableUserCommand command = new DisableUserCommand(userId);

        Mockito.when(repository.findById(userId)).thenReturn(user);
        Mockito.when(repository.save(any(User.class))).thenReturn(user);

        useCase.execute(command, userId);

        assertFalse(user.isActive(), "User should be inactive after disable");
        Mockito.verify(repository).findById(userId);
        Mockito.verify(repository).save(user);
    }

    @Test
    void shouldThrowForbiddenExceptionWhenUserIsNotAuthorized() throws BusinessException, EntityNotFoundException {
        final DisableUserCommand command = new DisableUserCommand(1L);
        final Long loggedUserId = 2L;

        final ForbiddenException exception = assertThrows(
                ForbiddenException.class,
                () -> useCase.execute(command, loggedUserId)
        );

        assertEquals("Unauthorized", exception.getMessage());
        Mockito.verify(repository, Mockito.never()).findById(any());
        Mockito.verify(repository, Mockito.never()).save(any());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUserDoesNotExist() throws Exception {
        final Long userId = 1L;
        final DisableUserCommand command = new DisableUserCommand(userId);

        Mockito.when(repository.findById(userId))
                .thenThrow(new EntityNotFoundException("User not found"));

        assertThrows(
                EntityNotFoundException.class,
                () -> useCase.execute(command, userId)
        );

        Mockito.verify(repository).findById(userId);
        Mockito.verify(repository, Mockito.never()).save(any());
    }

    @Test
    void shouldPropagateBusinessExceptionFromDomain() throws Exception {
        final Long userId = 1L;
        final DisableUserCommand command = new DisableUserCommand(userId);

        Mockito.when(repository.findById(userId))
                .thenThrow(new BusinessException("Invalid user state"));

        assertThrows(
                BusinessException.class,
                () -> useCase.execute(command, userId)
        );

        Mockito.verify(repository).findById(userId);
        Mockito.verify(repository, Mockito.never()).save(any());
    }
}
