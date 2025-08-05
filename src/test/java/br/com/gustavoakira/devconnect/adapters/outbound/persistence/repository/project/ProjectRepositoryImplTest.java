package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.project;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.ProjectEntity;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectRepositoryImplTest {

    @Mock
    private SpringDataPostgresProjectRepository springDataRepo;

    @InjectMocks
    private ProjectRepositoryImpl repository;

    private ProjectEntity sampleEntity;
    private Project sampleDomain;

    @BeforeEach
    void setup() throws BusinessException {
        sampleEntity = new ProjectEntity();
        sampleEntity.setId(1L);
        sampleEntity.setName("Project Test");
        sampleEntity.setDescription("dsafdsdsafdsfdsafdsafdsdfa");
        sampleEntity.setRepoUrl("https://github.com/Gustavo-Akira");
        sampleEntity.setDevProfileId(1L);

        sampleDomain = new Project(1L, "Project Test","afdsafdsafdsdasfdsdfsdsafadsf","https://github.com/Gustavo-Akira",1L);
    }

    @Nested
    class FindProjectById {
        @Test
        void shouldReturnProject_whenIdExists() throws Exception {
            Mockito.when(springDataRepo.findById(1L)).thenReturn(Optional.of(sampleEntity));

            final Project result = repository.findProjectById(1L);

            assertNotNull(result);
            assertEquals(sampleEntity.getId(), result.getId());
        }

        @Nested
        class WhenFails {
            @Test
            void shouldThrowEntityNotFoundException_whenIdDoesNotExist() {
                Mockito.when(springDataRepo.findById(2L)).thenReturn(Optional.empty());

                final EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                        () -> repository.findProjectById(2L));

                assertEquals("Project not found with id 2", ex.getMessage());
            }
        }
    }

    @Nested
    class FindAllProjectByDevId {
        @Test
        void shouldReturnPaginatedResult() throws Exception {
            final Pageable pageable = PageRequest.of(0, 10);
            final Page<ProjectEntity> page = new PageImpl<>(List.of(sampleEntity), pageable, 1);
            Mockito.when(springDataRepo.findAllByDevProfileId(1L, pageable)).thenReturn(page);

            final PaginatedResult<Project> result = repository.findAllProjectByDevId(1L, 0, 10);

            assertEquals(1, result.getTotalElements());
            assertEquals(1, result.getContent().size());
        }
    }

    @Nested
    class FindAllProject {
        @Test
        void shouldReturnPaginatedResult() throws Exception {
            final Pageable pageable = PageRequest.of(0, 5);
            final Page<ProjectEntity> page = new PageImpl<>(List.of(sampleEntity), pageable, 1);
            Mockito.when(springDataRepo.findAll(pageable)).thenReturn(page);

            final PaginatedResult<Project> result = repository.findAllProject(0, 5);

            assertEquals(1, result.getTotalElements());
            assertEquals(1, result.getContent().size());
        }
    }

    @Nested
    class CreateProject {
        @Test
        void shouldSaveAndReturnProject() throws Exception {
            Mockito.when(springDataRepo.save(Mockito.any(ProjectEntity.class))).thenReturn(sampleEntity);

            final Project saved = repository.createProject(sampleDomain);

            assertNotNull(saved);
            assertEquals(sampleDomain.getName(), saved.getName());
        }
    }

    @Nested
    class DeleteProject {
        @Nested
        class WhenSuccess {
            @Test
            void shouldDeleteWithoutException() throws Exception {
                Mockito.when(springDataRepo.existsById(1L)).thenReturn(true);
                Mockito.doNothing().when(springDataRepo).deleteById(1L);

                assertDoesNotThrow(() -> repository.deleteProject(1L));
                Mockito.verify(springDataRepo).deleteById(1L);
            }
        }

        @Nested
        class WhenFails {
            @Test
            void shouldThrowEntityNotFoundException_whenIdDoesNotExist() {
                Mockito.when(springDataRepo.existsById(2L)).thenReturn(false);

                final EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                        () -> repository.deleteProject(2L));

                assertEquals("Project not found with id 2", ex.getMessage());
            }
        }
    }

    @Nested
    class UpdateProject {
        @Nested
        class WhenSuccess {
            @Test
            void shouldUpdateAndReturnProject() throws Exception {
                Mockito.when(springDataRepo.existsById(sampleDomain.getId())).thenReturn(true);
                Mockito.when(springDataRepo.save(Mockito.any(ProjectEntity.class))).thenReturn(sampleEntity);

                final Project updated = repository.updateProject(sampleDomain);

                assertNotNull(updated);
                assertEquals(sampleDomain.getName(), updated.getName());
            }
        }

        @Nested
        class WhenFails {
            @Test
            void shouldThrowEntityNotFoundException_whenIdDoesNotExist() {
                Mockito.when(springDataRepo.existsById(sampleDomain.getId())).thenReturn(false);

                final EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                        () -> repository.updateProject(sampleDomain));

                assertEquals("Project not found with id " + sampleDomain.getId(), ex.getMessage());
            }
        }
    }
}