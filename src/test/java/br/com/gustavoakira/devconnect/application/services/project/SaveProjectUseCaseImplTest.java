package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.usecases.project.SaveProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveProjectUseCaseImplTest {
    @InjectMocks
    private SaveProjectUseCaseImpl useCase;
    @Mock
    private IProjectRepository projectRepository;



    @Nested
    class SaveWithSuccess{
        @BeforeEach
        void setup() throws BusinessException {
            Mockito.when(projectRepository.createProject(Mockito.any())).thenReturn(new Project(1L,"akira","agdgsdgdgsdsgdgsagd","https://github.com/",2L));
        }
        @Test
        void shouldReturnProjectWhenProjectIsValidAndSavedWithSuccessOnRepository() throws BusinessException {
            final Project project = new Project("akira","agdgsdgdgsdsgdgsagd","https://github.com/",2L);
            final Project savedProject = useCase.execute(project);
            assertEquals(1L, savedProject.getId());
            assertEquals("akira",savedProject.getName());
            assertEquals("https://github.com/",savedProject.getRepoUrl());
            assertEquals("agdgsdgdgsdsgdgsagd", savedProject.getDescription());
            assertEquals(2L, savedProject.getDevProfileId());
        }
    }

    @Nested
    class Exception{
        @BeforeEach
        void setup() throws BusinessException {
            Mockito.when(projectRepository.createProject(Mockito.any())).thenThrow(new BusinessException("Invalid Project"));
        }

        @Test
        void shouldThrowBusinessExceptionWhenProjectIsInvalid(){
            assertThrows(BusinessException.class,()->useCase.execute(new Project("akira","adsgdgsaagdsgdsa","https://github.com/",2L)));
        }
    }
}