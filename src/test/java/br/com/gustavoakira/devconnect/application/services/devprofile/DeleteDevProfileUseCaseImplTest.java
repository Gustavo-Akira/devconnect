package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.ForbiddenException;
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
        void shouldDeleteWithSuccessWhenDevProfileExistsAndLoggedUserIsEqual() throws EntityNotFoundException {
            Mockito.doNothing().when(repository).deleteProfile(1L);
            useCase.execute(new DeleteDevProfileCommand(1L),1L);
        }
    }
    @Nested
    class DeleteWithException{
        @Test
        void shouldThrowEntityNotFoundExceptionWhenDevProfileNotFound() throws EntityNotFoundException {
            Mockito.doThrow(new EntityNotFoundException("")).when(repository).deleteProfile(1L);
            assertThrows(EntityNotFoundException.class,()->useCase.execute(new DeleteDevProfileCommand(1L),1L));
        }

        @Test
        void shouldThrowForbiddenExceptionWhenDeletedIdIsDifferentFromLoggedUserId() {
            assertThrows(ForbiddenException.class,()->useCase.execute(new DeleteDevProfileCommand(1L),2L));
        }
    }
}