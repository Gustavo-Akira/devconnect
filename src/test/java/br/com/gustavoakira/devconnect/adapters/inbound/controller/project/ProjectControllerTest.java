package br.com.gustavoakira.devconnect.adapters.inbound.controller.project;

import br.com.gustavoakira.devconnect.adapters.config.JwtProvider;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.DevProfileController;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.project.dto.CreateProjectRequest;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.project.dto.UpdateProjectRequest;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.project.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ProjectController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private SaveProjectUseCase saveProjectUseCase;

    @Mock
    private FindProjectByIdUseCase findProjectByIdUseCase;

    @Mock
    private UpdateProjectUseCase updateProjectUseCase;

    @Mock
    private DeleteProjectUseCase deleteProjectUseCase;

    @Mock
    private FindAllByDevProfileUseCase findAllByDevProfileUseCase;

    @MockitoBean
    private ProjectUseCases useCases;

    @BeforeEach
    void setup(){
        final var auth = new UsernamePasswordAuthenticationToken("1", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
        Mockito.when(useCases.getSaveProjectUseCase()).thenReturn(saveProjectUseCase);
        Mockito.when(useCases.getFindProjectByIdUseCase()).thenReturn(findProjectByIdUseCase);
        Mockito.when(useCases.getUpdateProjectUseCase()).thenReturn(updateProjectUseCase);
        Mockito.when(useCases.getDeleteProjectUseCase()).thenReturn(deleteProjectUseCase);
        Mockito.when(useCases.getFindAllByDevProfileUseCase()).thenReturn(findAllByDevProfileUseCase);
    }

    @Nested
    class SaveProjectEndpoint{
        @Test
        void shouldSaveProjectAndReturn201HttpStatusCode() throws Exception {
            final String projectName = "akira";
            final String projectDescription = "description";
            final String projectUrl = "https://github.com";
            Mockito.when(saveProjectUseCase.execute(Mockito.any())).thenReturn(new Project(1L,projectName,projectDescription,projectUrl,1L));
            final CreateProjectRequest request = new CreateProjectRequest(
                    projectName,
                    projectDescription,
                    projectUrl
            );

            mockMvc.perform(post("/v1/projects")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", containsString("/v1/projects/1")))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is(projectName)))
                    .andExpect(jsonPath("$.description", is(projectDescription)))
                    .andExpect(jsonPath("$.devProfileId", is(1)))
                    .andExpect(jsonPath("$.repoUrl", is(projectUrl)));
        }
    }
    @Nested
    class FindById{
        @Test
        void shouldReturn200HttpStatusWhenProjectExists() throws Exception {
            final String projectName = "akira";
            final String projectDescription = "description";
            final String projectUrl = "https://github.com";
            Mockito.when(findProjectByIdUseCase.execute(1L)).thenReturn(new Project(1L,projectName,projectDescription,projectUrl,1L));
            mockMvc.perform(get("/v1/projects/1"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is(projectName)))
                    .andExpect(jsonPath("$.description", is(projectDescription)))
                    .andExpect(jsonPath("$.devProfileId", is(1)))
                    .andExpect(jsonPath("$.repoUrl", is(projectUrl)));
        }
    }

    @Nested
    class UpdateProject{
        @Test
        void shouldSaveProjectAndReturn200HttpStatusCode() throws Exception {
            final String projectName = "akira";
            final String projectDescription = "description";
            final String projectUrl = "https://github.com";

            final UpdateProjectRequest request = new UpdateProjectRequest(
                    1L,
                    projectName,
                    projectDescription,
                    projectUrl
            );
            Mockito.when(updateProjectUseCase.execute(request.toDomain(1L))).thenReturn(new Project(1L,projectName,projectDescription,projectUrl,1L));


            mockMvc.perform(put("/v1/projects")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is(projectName)))
                    .andExpect(jsonPath("$.description", is(projectDescription)))
                    .andExpect(jsonPath("$.devProfileId", is(1)))
                    .andExpect(jsonPath("$.repoUrl", is(projectUrl)));
        }
    }

    @Nested
    class DeleteProject{
        @Test
        void shouldDeleteWithSuccessAndReturn204HttpStatusCode() throws Exception {
            Mockito.doNothing().when(deleteProjectUseCase).execute(1L);
            mockMvc.perform(delete("/v1/projects/1"))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    class  FindAllProjectByDevProfile{
        @Test
        void shouldFindAllWithSentParam() throws Exception {
            Mockito.when(findAllByDevProfileUseCase.execute(1L,1,1)).thenReturn(getMockedProjectPaginatedResult(1,1));
            mockMvc.perform(get("/v1/projects/dev-profile/1?page=1&size=1"))
                    .andExpect(status().is2xxSuccessful());
        }
        @Test
        void shouldFindAllWithDefaultParamWhenNotSend() throws Exception {
            Mockito.when(findAllByDevProfileUseCase.execute(1L,5,0)).thenReturn(getMockedProjectPaginatedResult(0,5));
            mockMvc.perform(get("/v1/projects/dev-profile/1"))
                    .andExpect(status().is2xxSuccessful());
        }

        private PaginatedResult<Project> getMockedProjectPaginatedResult(int page, int size) throws BusinessException {
            return new PaginatedResult<>(
                    Collections.singletonList(new Project(1L, "akira","asdfdsffdsadsaf","https://github.com/gustavo-Akira/devconnect",1L)),
                    size,
                    page,
                    1
            );
        }

    }
}