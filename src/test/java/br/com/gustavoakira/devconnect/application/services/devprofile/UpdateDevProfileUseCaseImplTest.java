package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto.UpdateDevProfileRequest;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.ForbiddenException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.DeleteDevProfileCommand;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.UpdateDevProfileCommand;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UpdateDevProfileUseCaseImplTest {
    @InjectMocks
    private UpdateDevProfileUseCaseImpl useCase;

    @Mock
    private IDevProfileRepository repository;

    @Nested
    class UpdateWithSuccess{
        @Test
        void shouldUpdateDevProfileWithSuccess() throws BusinessException {
            Mockito.when(repository.update(Mockito.any())).thenReturn(Mockito.any());
            assertDoesNotThrow(()->useCase.execute(getMockCommand(),1L));
        }
    }

    @Nested
    class UpdateWithException{
        @Test
        void shouldThrowBusinessExceptionWhenDevProfileWasModifiedDirectlyOnDB() throws BusinessException {
            Mockito.when(repository.update(Mockito.any())).thenThrow(BusinessException.class);
            assertThrows(BusinessException.class,()->useCase.execute(getMockCommand(),1L));
        }

        @Test
        void shouldThrowForbiddenExceptionWhenDeletedIdIsDifferentFromLoggedUserId() {
            assertThrows(ForbiddenException.class,()->useCase.execute(getMockCommand(),2L));
        }
    }

    private UpdateDevProfileCommand getMockCommand(){
        final UpdateDevProfileRequest request = new UpdateDevProfileRequest(1L, "Gustavo Akira",
                "gustavo@email.com",
                "123456",
                "Rua Teste",
                "São Paulo",
                "SP",
                "01234-567",
                "BR",
                "https://github.com/gustavo",
                "https://linkedin.com/in/gustavo",
                "Sou desenvolvedor Java",
                List.of("Java", "Spring Boot"));
        return request.toCommand();
    }
}