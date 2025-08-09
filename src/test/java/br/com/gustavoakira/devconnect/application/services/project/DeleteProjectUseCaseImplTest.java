package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteProjectUseCaseImplTest {
    @Mock
    private IProjectRepository repository;

    @InjectMocks
    private DeleteProjectUseCaseImpl useCase;

    @Test
    void shouldDeleteProjectWhenProjectIdExists() throws EntityNotFoundException {
        Mockito.doNothing().when(repository).deleteProject(1L);
        assertDoesNotThrow(()->useCase.execute(1L));
    }
}