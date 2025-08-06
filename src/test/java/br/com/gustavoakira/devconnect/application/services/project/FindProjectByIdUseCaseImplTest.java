package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
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
class FindProjectByIdUseCaseImplTest {
    @Mock
    private IProjectRepository repository;

    @InjectMocks
    private FindProjectByIdUseCaseImpl useCase;

    @Nested
    class Found{
        @BeforeEach
        void setup() throws BusinessException, EntityNotFoundException {
            Mockito.when(repository.findProjectById(1L)).thenReturn(new Project(1L,"akira","dsadsfsdfafads","https://github.com/",2L));
        }

        @Test
        void shouldReturnProjectWhenProjectExistAndIsValid() throws BusinessException, EntityNotFoundException {
            final Project project = useCase.execute(1L);
            assertEquals(1L, project.getId());
            assertEquals(2L,project.getDevProfileId());
            assertEquals("akira",project.getName());
            assertEquals("dsadsfsdfafads",project.getDescription());
            assertEquals("https://github.com/",project.getRepoUrl());
        }
    }

    @Nested
    class ThrowEntityNotFound{
        @BeforeEach
        void setup() throws BusinessException, EntityNotFoundException {
            Mockito.when(repository.findProjectById(Mockito.any())).thenThrow(new EntityNotFoundException(""));
        }

        @Test
        void shouldThrowEntityNotFoundExceptionWhenProjectDoesNotExist(){
            assertThrows(EntityNotFoundException.class,()->useCase.execute(1L));
        }
    }

    @Nested
    class ThrowBusinessException{
        @BeforeEach
        void setup() throws BusinessException, EntityNotFoundException {
            Mockito.when(repository.findProjectById(Mockito.any())).thenThrow(new BusinessException(""));
        }

        @Test
        void shouldThrowEntityNotFoundExceptionWhenProjectDoesNotExist(){
            assertThrows(BusinessException.class,()->useCase.execute(1L));
        }
    }
}