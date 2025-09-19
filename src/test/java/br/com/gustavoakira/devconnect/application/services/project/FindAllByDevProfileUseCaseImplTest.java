package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindAllByDevProfileUseCaseImplTest {

    @InjectMocks
    private FindAllByDevProfileUseCaseImpl useCase;

    @Mock
    private IProjectRepository repository;

    @Mock
    private IDevProfileRepository devProfileRepository;

    @Nested
    class NoProjects{
        @BeforeEach
        void setup() throws BusinessException {
            Mockito.when(repository.findAllProjectByDevId(1L, 0,5)).thenReturn(new PaginatedResult<>(Collections.emptyList(),0,1,1));
        }

        @Test
        void shouldReturnPaginatedResultWithProjects(){
            assertDoesNotThrow(()->useCase.execute(1L,5,0));
        }
    }

    @Nested
    class AllProjectsAreValid{
        @BeforeEach
        void setup() throws BusinessException, EntityNotFoundException {

            final DevProfile profile = new DevProfile("João Silva", "joao@email.com", "Str0ng@Pwd", "Desenvolvedor backend com 10 anos de experiência.", new Address("Rua A", "Cidade X", "Estado Y", "BR", "12345-678"), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>(),true);
            Mockito.when(repository.findAllProjectByDevId(1L, 0,5)).thenReturn(new PaginatedResult<>(List.of(new Project(1L,"sfdsasfdfds","dsasdfdsadsf","https://github.com",1L)),0,1,1));
            Mockito.when(devProfileRepository.findById(1L)).thenReturn(profile);
        }

        @Test
        void shouldReturnPaginatedResultWithProjects(){
            assertDoesNotThrow(()->useCase.execute(1L,5,0));
        }
    }

    @Nested
    class AnyOrAllProjectInvalid{
        @BeforeEach
        void setup() throws BusinessException {
            Mockito.when(repository.findAllProjectByDevId(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt())).thenThrow(BusinessException.class);
        }

        @Test
        void shouldThrowBusinessException(){
            assertThrows(BusinessException.class, ()-> useCase.execute(1L,1,1));
        }
    }
}