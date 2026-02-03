package br.com.gustavoakira.devconnect.application.services.auth;

import br.com.gustavoakira.devconnect.adapters.config.JwtProvider;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.auth.dto.TokenRequest;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TokenGrantUseCaseImplTest {

    @Mock
    private JwtProvider provider;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private TokenGrantUseCaseImpl tokenGrantUseCase;

    private static final String VALID_EMAIL = "akirauekita2002@gmail.com";
    private static final String VALID_PASSWORD = "K@deira.0";

    @Nested
    class GrantTypeValidation {

        @Test
        void shouldThrowBusinessExceptionWhenGrantTypeIsNotPassword() {
            final TokenRequest invalidGrantType = new TokenRequest("client_credentials", VALID_EMAIL, VALID_PASSWORD);

            final BusinessException exception = assertThrows(BusinessException.class, () ->
                    tokenGrantUseCase.execute(invalidGrantType.toCommand()));

            assertEquals("Unsupported grant_type client_credentials", exception.getMessage());
        }
    }

    @Nested
    class EmailValidation {

        @Test
        void shouldThrowEntityNotFoundExceptionWhenEmailIsNotFound() throws BusinessException, EntityNotFoundException {
            Mockito.when(userRepository.findByEmail("notfound@gmail.com")).thenThrow(new EntityNotFoundException("User not found"));

            final TokenRequest invalidEmail = new TokenRequest("password", "notfound@gmail.com", VALID_PASSWORD);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                    tokenGrantUseCase.execute(invalidEmail.toCommand()));

            assertEquals("User not found", exception.getMessage());
        }
    }

    @Nested
    class PasswordValidation {

        @BeforeEach
        void setup() throws BusinessException, EntityNotFoundException {
            Mockito.when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(new User(1L,"Str@ngP4ssword",VALID_EMAIL,true));
        }

        @Test
        void shouldThrowBusinessExceptionWhenPasswordDoesNotMatch() {
            Mockito.when(encoder.matches("wrongPassword", "Str@ngP4ssword")).thenReturn(false);

            final TokenRequest wrongPasswordRequest = new TokenRequest("password", VALID_EMAIL, "wrongPassword");

            final BusinessException exception = assertThrows(BusinessException.class, () ->
                    tokenGrantUseCase.execute(wrongPasswordRequest.toCommand()));

            assertEquals("Invalid credentials", exception.getMessage());
        }
    }

    @Nested
    class SuccessFlow {

        @BeforeEach
        void setup() throws BusinessException, EntityNotFoundException {
            Mockito.when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(new User(1L,"Str@ngP4ssword",VALID_EMAIL,true));
            Mockito.when(encoder.matches(VALID_PASSWORD, "Str@ngP4ssword")).thenReturn(true);
            Mockito.when(provider.generateToken(Mockito.any(), Mockito.any(Long.class))).thenReturn("mocked-token");
        }

        @Test
        void shouldReturnTokenWhenCredentialsAreValid() throws BusinessException, EntityNotFoundException {
            final TokenRequest validRequest = new TokenRequest("password", VALID_EMAIL, VALID_PASSWORD);

            final var response = tokenGrantUseCase.execute(validRequest.toCommand());

            assertNotNull(response);
            assertEquals("mocked-token", response.token());
        }
    }

}