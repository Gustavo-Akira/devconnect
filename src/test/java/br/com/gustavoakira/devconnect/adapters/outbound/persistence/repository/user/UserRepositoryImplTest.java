package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.user;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.UserEntity;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private SpringDataPostgresUserRepository springRepository;

    @Mock
    private PasswordEncoder encoder;

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepositoryImpl(springRepository,encoder);
    }

    @Test
    void shouldFindUserByIdSuccessfully() throws Exception {
        final var entity = mock(UserEntity.class);
        final var domainUser = mock(User.class);

        when(springRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(entity.toDomain()).thenReturn(domainUser);

        final User result = userRepository.findById(1L);

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
        final var entity = mock(UserEntity.class);

        when(springRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(entity.toDomain()).thenThrow(new BusinessException("Invalid domain state"));

        assertThrows(
                BusinessException.class,
                () -> userRepository.findById(1L)
        );
    }

    @Test
    void shouldFindUserByEmailSuccessfully() throws Exception {
        final var entity = mock(UserEntity.class);
        final var domainUser = mock(User.class);

        when(springRepository.findByEmail("test@email.com")).thenReturn(Optional.of(entity));
        when(entity.toDomain()).thenReturn(domainUser);

        final User result = userRepository.findByEmail("test@email.com");

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

    @Test
    void shouldSaveAndReturnUserWhenUserInfoIsValid() throws BusinessException {
        final UserEntity entity = new UserEntity();
        entity.setEmail("alora@gmail.com");
        entity.setPassword("password");
        entity.setIsActive(true);
        final User domainUser =entity.toDomain();
        Mockito.when(encoder.encode(Mockito.any())).thenReturn("asfdsfsasaf");
        when(springRepository.save(any())).thenReturn(entity);
        final User result = userRepository.save(domainUser);
        assertEquals(domainUser.getEmail(), result.getEmail());
        assertEquals(domainUser.getPassword().getValue(), result.getPassword().getValue());
        assertEquals(domainUser.isActive(), result.isActive());
    }

    @Test
    void shouldThrowBusinessExceptionWhenUserInfoIsInvalid() throws BusinessException {
        final UserEntity entity = new UserEntity();
        entity.setEmail("alora@gmail.com");
        entity.setPassword("password");
        entity.setIsActive(true);
        final User domainUser =entity.toDomain();
        final UserEntity result = new UserEntity();
        result.setEmail("akira ");
        when(springRepository.save(any())).thenReturn(result);
        assertThrows(BusinessException.class,()-> userRepository.save(domainUser));
    }
}
