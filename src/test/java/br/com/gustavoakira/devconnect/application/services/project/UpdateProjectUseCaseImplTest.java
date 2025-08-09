package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.Project;
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
class UpdateProjectUseCaseImplTest {

    @Mock
    private IProjectRepository repository;

    @InjectMocks
    private UpdateProjectUseCaseImpl useCase;

    @Test
    void shouldUpdateAndReturnProjectWhenUseCaseIsCalled() throws BusinessException, EntityNotFoundException {
        Mockito.when(repository.updateProject(Mockito.any())).thenReturn(Mockito.any());
        assertDoesNotThrow(()->useCase.execute(new Project(1L,"akira","dfssdsdaffdsfadsf","https://github.com",1L)));
    }
}