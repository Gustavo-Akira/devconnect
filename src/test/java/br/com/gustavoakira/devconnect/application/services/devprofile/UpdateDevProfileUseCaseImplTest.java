package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto.UpdateDevProfileRequest;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.ForbiddenException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.UpdateDevProfileCommand;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UpdateDevProfileUseCaseImplTest {
    @InjectMocks
    private UpdateDevProfileUseCaseImpl useCase;

    @Mock
    private IDevProfileRepository repository;

    final DevProfile profile = new DevProfile(1L,2L,"João Silva", "joao@email.com", "Str0ng@Pwd", "Desenvolvedor backend com 10 anos de experiência.",  new Address("Rua A", "Cidade X", "Estado Y", "BR", "12345-678"), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>(),true);

    UpdateDevProfileUseCaseImplTest() throws BusinessException {
    }


    @Nested
    class UpdateWithSuccess{

        @Test
        void shouldUpdateDevProfileWithSuccess() throws BusinessException, EntityNotFoundException {
            Mockito.when(repository.findById(Mockito.any())).thenReturn(profile);
            Mockito.when(repository.update(Mockito.any())).thenReturn(Mockito.any());
            assertDoesNotThrow(()->useCase.execute(getMockCommand(),2L));
        }
    }

    @Nested
    class UpdateWithException{
        @Test
        void shouldThrowBusinessExceptionWhenDevProfileWasModifiedDirectlyOnDB() throws BusinessException, EntityNotFoundException {
            Mockito.when(repository.findById(Mockito.any())).thenReturn(profile);
            Mockito.when(repository.update(Mockito.any())).thenThrow(BusinessException.class);
            assertThrows(BusinessException.class,()->useCase.execute(getMockCommand(),2L));
        }

        @Test
        void shouldThrowForbiddenExceptionWhenDeletedIdIsDifferentFromLoggedUserId() throws BusinessException, EntityNotFoundException {
            Mockito.when(repository.findById(Mockito.any())).thenReturn(profile);
            assertThrows(ForbiddenException.class,()->useCase.execute(getMockCommand(),3L));
        }

        @Test
        void shouldThrowEntityNotFoundWhenDevProfileIdNotExistOnDB() throws BusinessException, EntityNotFoundException {
            Mockito.when(repository.findById(Mockito.any())).thenThrow(EntityNotFoundException.class);
            assertThrows(EntityNotFoundException.class, ()->useCase.execute(getMockCommand(),1L));
        }
    }

    private UpdateDevProfileCommand getMockCommand(){
        final UpdateDevProfileRequest request = new UpdateDevProfileRequest(1L, "Gustavo Akira",
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