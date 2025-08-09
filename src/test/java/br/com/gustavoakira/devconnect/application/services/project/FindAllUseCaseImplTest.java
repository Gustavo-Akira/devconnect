package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindAllUseCaseImplTest {
    @Mock
    private IProjectRepository repository;

    @InjectMocks
    private FindAllUseCaseImpl useCase;
    @Test
    void shouldFindAllProject() throws BusinessException {
        Mockito.when(repository.findAllProject(0,1)).thenReturn(Mockito.mock());
        assertDoesNotThrow(()->useCase.execute(1,0));
    }
}