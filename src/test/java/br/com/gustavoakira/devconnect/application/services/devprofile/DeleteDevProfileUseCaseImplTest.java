package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.ForbiddenException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.DeleteDevProfileCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteDevProfileUseCaseImplTest {

    @InjectMocks
    private DeleteDevProfileUseCaseImpl useCase;

    @Mock
    private IDevProfileRepository repository;


    @Nested
    class DeleteWithSuccess{
        @Test
        void shouldDeleteWithSuccessWhenDevProfileExistsAndLoggedUserIsEqual() throws EntityNotFoundException, BusinessException {
            Mockito.doNothing().when(repository).deleteProfile(1L);
            final DevProfile profile = createMock(1L);
            profile.setId(1L);
            Mockito.when(repository.findById(1L)).thenReturn(profile);
            useCase.execute(new DeleteDevProfileCommand(1L),1L);
        }
    }
    @Nested
    class DeleteWithException{
        @Test
        void shouldThrowEntityNotFoundExceptionWhenDevProfileNotFound() throws EntityNotFoundException, BusinessException {
            Mockito.when(repository.findById(1L)).thenThrow(new EntityNotFoundException(""));
            assertThrows(EntityNotFoundException.class,()->useCase.execute(new DeleteDevProfileCommand(1L),1L));
        }

        @Test
        void shouldThrowForbiddenExceptionWhenDeletedIdIsDifferentFromLoggedUserId() throws BusinessException, EntityNotFoundException {
            final DevProfile profile = createMock(2L);
            Mockito.when(repository.findById(1L)).thenReturn(profile);
            assertThrows(ForbiddenException.class,()->useCase.execute(new DeleteDevProfileCommand(1L),3L));
        }
    }

    private DevProfile createMock(Long userId) throws BusinessException {
        return new DevProfile(1L,userId,"João Silva", "joao@email.com", "Str0ng@Pwd", "Desenvolvedor backend com 10 anos de experiência.",  new Address("Rua A", "Cidade X", "Estado Y", "BR", "12345-678"), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>(),true);
    }
}