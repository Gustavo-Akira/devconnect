package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
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
class FindAllUseCaseImplTest {
    @Mock
    private IProjectRepository repository;
    @Mock
    private IDevProfileRepository devProfileRepository;
    @InjectMocks
    private FindAllUseCaseImpl useCase;
    @Test
    void shouldFindAllProject() throws BusinessException {
        Mockito.when(repository.findAllProject(0,1)).thenReturn(Mockito.mock());
        assertDoesNotThrow(()->useCase.execute(1,0));
    }

    @Test
    void shouldFindAllProjectWithOwner() throws BusinessException, EntityNotFoundException {
        final DevProfile profile = new DevProfile("João Silva", "joao@email.com", "Str0ng@Pwd", "Desenvolvedor backend com 10 anos de experiência.", new Address("Rua A", "Cidade X", "Estado Y", "BR", "12345-678"), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>(),true);
        Mockito.when(repository.findAllProject(0,1)).thenReturn(new PaginatedResult<>(List.of(new Project(1L,"sfdsasfdfds","dsasdfdsadsf","https://github.com",1L)),0,1,1));
        Mockito.when(devProfileRepository.findById(1L)).thenReturn(profile);
        assertDoesNotThrow(()->useCase.execute(1,0));
    }

    @Test
    void shouldThrowWithInvalidOwner() throws BusinessException, EntityNotFoundException {
        Mockito.when(repository.findAllProject(0,1)).thenReturn(new PaginatedResult<>(List.of(new Project(1L,"sfdsasfdfds","dsasdfdsadsf","https://github.com",1L)),0,1,1));
        Mockito.when(devProfileRepository.findById(1L)).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class,()->useCase.execute(1,0));
    }
}