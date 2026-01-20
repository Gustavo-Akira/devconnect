package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.user;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.UserEntity;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private SpringDataPostgresUserRepository springRepository;

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepositoryImpl(springRepository);
    }

    @Test
    void shouldFindUserByIdSuccessfully() throws Exception {
        var entity = mock(UserEntity.class);
        var domainUser = mock(User.class);

        when(springRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(entity.toDomain()).thenReturn(domainUser);

        User result = userRepository.findById(1L);

        assertNotNull(result);
        assertEquals(domainUser, result);
        verify(springRepository).findById(1L);
        verify(entity).toDomain();
    }

    @Test
    void shouldThrowEntityNotFoundWhenUserIdDoesNotExist() {
        when(springRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> userRepository.findById(99L)
        );
    }

    @Test
    void shouldPropagateBusinessExceptionWhenDomainIsInvalid() throws Exception {
        var entity = mock(UserEntity.class);

        when(springRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(entity.toDomain()).thenThrow(new BusinessException("Invalid domain state"));

        assertThrows(
                BusinessException.class,
                () -> userRepository.findById(1L)
        );
    }

    @Test
    void shouldFindUserByEmailSuccessfully() throws Exception {
        var entity = mock(UserEntity.class);
        var domainUser = mock(User.class);

        when(springRepository.findByEmail("test@email.com")).thenReturn(Optional.of(entity));
        when(entity.toDomain()).thenReturn(domainUser);

        User result = userRepository.findByEmail("test@email.com");

        assertNotNull(result);
        assertEquals(domainUser, result);
        verify(springRepository).findByEmail("test@email.com");
        verify(entity).toDomain();
    }

    @Test
    void shouldThrowEntityNotFoundWhenEmailDoesNotExist() {
        when(springRepository.findByEmail("invalid@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> userRepository.findByEmail("invalid@email.com")
        );
    }
}
