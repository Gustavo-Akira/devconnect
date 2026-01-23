package br.com.gustavoakira.devconnect.application.services.user;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.user.query.FindUserByIdQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindUserByIdUseCaseImplTest {
    @Mock
    private IUserRepository repository;

    @InjectMocks
    private FindUserByIdUseCaseImpl useCase;

    @Test
    void shouldReturnUserWhenRepositoryReturnsIt() throws BusinessException, EntityNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(new User(1L,"Gustavo Akira","password","akirauekita2002@gmail.com",true));
        final User result = useCase.execute(new FindUserByIdQuery(1L));
        assertEquals(1L, result.getId());
        assertEquals("password", result.getPassword().getValue());
        assertEquals("akirauekita2002@gmail.com", result.getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void shouldThrowsBusinessExceptionWhenUserIsInvalid() throws BusinessException, EntityNotFoundException {
        Mockito.when(repository.findById(1L)).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class, ()-> useCase.execute(new FindUserByIdQuery(1L)));
    }

    @Test
    void shouldThrowsEntityNotFoundExceptionWhenUserIsNotFound() throws BusinessException, EntityNotFoundException {
        Mockito.when(repository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, ()-> useCase.execute(new FindUserByIdQuery(1L)));
    }
}