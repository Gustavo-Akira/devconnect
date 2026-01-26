package br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile;

import br.com.gustavoakira.devconnect.adapters.config.JwtProvider;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto.SaveDevProfileRequest;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto.UpdateDevProfileRequest;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.*;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.DeleteDevProfileCommand;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.filters.DevProfileFilter;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.query.DevProfileFindAllQuery;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.query.FindDevProfileByIdQuery;
import br.com.gustavoakira.devconnect.application.usecases.user.DisableUserUseCase;
import br.com.gustavoakira.devconnect.application.usecases.user.FindUserByIdUseCase;
import br.com.gustavoakira.devconnect.application.usecases.user.command.DisableUserCommand;
import br.com.gustavoakira.devconnect.application.usecases.user.query.FindUserByIdQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DevProfileController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
class DevProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DevProfileUseCases useCases;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private SaveDevProfileUseCase saveUseCase;

    @Mock
    private UpdateDevProfileUseCase updateDevProfileUseCase;

    @Mock
    private DeleteDevProfileUseCase deleteDevProfileUseCase;

    @Mock
    private FindDevProfileByIdUseCase findDevProfileByIdUseCase;

    @Mock
    private FindAllDevProfileUseCase findAllDevProfileUseCase;

    @Mock
    private FindAllDevProfileWithFilterUseCase findAllDevProfileWithFilterUseCase;

    @MockitoBean
    private FindUserByIdUseCase findUserByIdUseCase;
    @MockitoBean
    private DisableUserUseCase disableUserUseCase;

    @BeforeEach
    void setup() throws BusinessException {
        MockitoAnnotations.openMocks(this);
        final var auth = new UsernamePasswordAuthenticationToken("1", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
        Mockito.when(useCases.saveDevProfileUseCase()).thenReturn(saveUseCase);
        Mockito.when(useCases.updateDevProfileUseCase()).thenReturn(updateDevProfileUseCase);
        Mockito.when(useCases.deleteDevProfileUseCase()).thenReturn(deleteDevProfileUseCase);
        Mockito.when(useCases.findDevProfileByIdUseCase()).thenReturn(findDevProfileByIdUseCase);
        Mockito.when(useCases.findAllDevProfileUseCase()).thenReturn(findAllDevProfileUseCase);
        Mockito.when(useCases.findAllDevProfileWithFilterUseCase()).thenReturn(findAllDevProfileWithFilterUseCase);
    }

    private User createMockUser() throws BusinessException {
        return new User(
                1L,
                "Gustavo Akira",
                "123456",
                "gustavo@email.com",
                true
        );
    }

    @Nested
    class Create{
        @Test
        void shouldCreateDevProfile() throws Exception {
            final SaveDevProfileRequest request = new SaveDevProfileRequest(
                    "Gustavo Akira",
                    "gustavo@email.com",
                    "123456",
                    "Rua Teste",
                    "São Paulo",
                    "SP",
                    "01234-567",
                    "BR",
                    "https://github.com/gustavo",
                    "https://linkedin.com/in/gustavo",
                    "Sou desenvolvedor Java",
                    List.of("Java", "Spring Boot")
            );

            final DevProfile savedProfile = createMockDevProfile();
            final User user = createMockUser();

            Mockito.when(saveUseCase.execute(any())).thenReturn(savedProfile);
            Mockito.when(findUserByIdUseCase.execute(new FindUserByIdQuery(savedProfile.getUserId())))
                    .thenReturn(user);

            mockMvc.perform(post("/v1/dev-profiles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.email", is("gustavo@email.com")));
        }
    }
    @Nested
    class UpdateDevProfile{
        @Test
        void shouldUpdateDevProfile() throws Exception {
            final UpdateDevProfileRequest request = new UpdateDevProfileRequest(1L, "Gustavo Akira",
                    "Rua Teste",
                    "São Paulo",
                    "SP",
                    "01234-567",
                    "BR",
                    "https://github.com/gustavo",
                    "https://linkedin.com/in/gustavo",
                    "Sou desenvolvedor Java",
                    List.of("Java", "Spring Boot"));

            final DevProfile updatedProfile = createMockDevProfile();
            Mockito.when(findUserByIdUseCase.execute(new FindUserByIdQuery(updatedProfile.getUserId())))
                    .thenReturn(createMockUser());

            Mockito.when(updateDevProfileUseCase.execute(any(),any())).thenReturn(updatedProfile);

            mockMvc.perform(put("/v1/dev-profiles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Gustavo Akira")));
        }
    }

    @Nested
    class DeleteDevProfile {

        @Test
        void shouldDisableUserWhenDeletingDevProfile() throws Exception {
            final DevProfile profile = createMockDevProfile();

            Mockito.when(
                    findDevProfileByIdUseCase.execute(
                            new FindDevProfileByIdQuery(1L)
                    )
            ).thenReturn(profile);

            mockMvc.perform(delete("/v1/dev-profiles/1"))
                    .andExpect(status().isNoContent());

            Mockito.verify(findDevProfileByIdUseCase)
                    .execute(new FindDevProfileByIdQuery(1L));

            Mockito.verify(disableUserUseCase)
                    .execute(
                            new DisableUserCommand(profile.getUserId()),
                            1L
                    );
        }
    }

    @Nested
    class GetDevProfileById{
        @Test
        void shouldReturnDevProfileById() throws Exception {
            final DevProfile profile = createMockDevProfile();
            final User user = createMockUser();

            Mockito.when(findDevProfileByIdUseCase.execute(new FindDevProfileByIdQuery(1L)))
                    .thenReturn(profile);
            Mockito.when(findUserByIdUseCase.execute(new FindUserByIdQuery(profile.getUserId())))
                    .thenReturn(user);

            mockMvc.perform(get("/v1/dev-profiles/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.email", is("gustavo@email.com")));
        }
    }


    @Nested
    class GetAllWithFilters{
        @Test
        void shouldReturnPaginatedListWithFilters() throws Exception {
            Mockito.when(findUserByIdUseCase.execute(any()))
                    .thenReturn(createMockUser());
            final DevProfile profile =createMockDevProfile();
            final PaginatedResult<DevProfile> result = new PaginatedResult<>(List.of(profile), 0, 5, 1L);

            Mockito.when(findAllDevProfileWithFilterUseCase
                            .execute(eq(new DevProfileFilter("Gustavo","SP", Collections.singletonList("java"))), eq(0), eq(5)))
                    .thenReturn(result);

            mockMvc.perform(get("/v1/dev-profiles?name=Gustavo&city=SP&stack=java"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.totalElements", is(1)));
        }


        @Test
        void shouldReturnPaginatedListWithFiltersOnlyCity() throws Exception {
            Mockito.when(findUserByIdUseCase.execute(any()))
                    .thenReturn(createMockUser());
            final DevProfile profile =createMockDevProfile();
            final PaginatedResult<DevProfile> result = new PaginatedResult<>(List.of(profile), 0, 5, 1L);

            Mockito.when(findAllDevProfileWithFilterUseCase
                            .execute(eq(new DevProfileFilter(null,"SP", null)), eq(0), eq(5)))
                    .thenReturn(result);

            mockMvc.perform(get("/v1/dev-profiles?city=SP"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.totalElements", is(1)));
        }

        @Test
        void shouldReturnPaginatedListWithFilterOnlyCityAndOtherEmpty() throws Exception {
            Mockito.when(findUserByIdUseCase.execute(any()))
                    .thenReturn(createMockUser());
            final DevProfile profile =createMockDevProfile();
            final PaginatedResult<DevProfile> result = new PaginatedResult<>(List.of(profile), 0, 5, 1L);

            Mockito.when(findAllDevProfileWithFilterUseCase
                            .execute(eq(new DevProfileFilter("","SP", Collections.emptyList())), eq(0), eq(5)))
                    .thenReturn(result);

            mockMvc.perform(get("/v1/dev-profiles?city=SP&name=&stack="))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.totalElements", is(1)));
        }

        @Test
        void shouldReturnPaginatedListWithFiltersOnlyName() throws Exception {
            Mockito.when(findUserByIdUseCase.execute(any()))
                    .thenReturn(createMockUser());
            final DevProfile profile =createMockDevProfile();
            final PaginatedResult<DevProfile> result = new PaginatedResult<>(List.of(profile), 0, 5, 1L);

            Mockito.when(findAllDevProfileWithFilterUseCase
                            .execute(eq(new DevProfileFilter("Gustavo",null, null)), eq(0), eq(5)))
                    .thenReturn(result);

            mockMvc.perform(get("/v1/dev-profiles?name=Gustavo"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.totalElements", is(1)));
        }

        @Test
        void shouldReturnPaginatedListWithFilterOnlyNameAndOtherEmpty() throws Exception {
            Mockito.when(findUserByIdUseCase.execute(any()))
                    .thenReturn(createMockUser());
            final DevProfile profile =createMockDevProfile();
            final PaginatedResult<DevProfile> result = new PaginatedResult<>(List.of(profile), 0, 5, 1L);

            Mockito.when(findAllDevProfileWithFilterUseCase
                            .execute(eq(new DevProfileFilter("Gustavo","", Collections.emptyList())), eq(0), eq(5)))
                    .thenReturn(result);

            mockMvc.perform(get("/v1/dev-profiles?name=Gustavo&city=&stack="))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.totalElements", is(1)));
        }

        @Test
        void shouldReturnPaginatedListWithFiltersOnlyStack() throws Exception {
            Mockito.when(findUserByIdUseCase.execute(any()))
                    .thenReturn(createMockUser());
            final DevProfile profile =createMockDevProfile();
            final PaginatedResult<DevProfile> result = new PaginatedResult<>(List.of(profile), 0, 5, 1L);

            Mockito.when(findAllDevProfileWithFilterUseCase
                            .execute(eq(new DevProfileFilter(null,null, Collections.singletonList("java"))), eq(0), eq(5)))
                    .thenReturn(result);

            mockMvc.perform(get("/v1/dev-profiles?stack=java"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.totalElements", is(1)));
        }

        @Test
        void shouldReturnPaginatedListWithFiltersOnlyStackAndOthersEmpty() throws Exception {
            Mockito.when(findUserByIdUseCase.execute(any()))
                    .thenReturn(createMockUser());
            final DevProfile profile =createMockDevProfile();
            final PaginatedResult<DevProfile> result = new PaginatedResult<>(List.of(profile), 0, 5, 1L);

            Mockito.when(findAllDevProfileWithFilterUseCase
                            .execute(eq(new DevProfileFilter("","", Collections.singletonList("java"))), eq(0), eq(5)))
                    .thenReturn(result);

            mockMvc.perform(get("/v1/dev-profiles?stack=java&city=&name="))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.totalElements", is(1)));
        }
    }


    @Nested
    class GetAllWithoutFilters{
        @Test
        void shouldReturnPaginatedListWithoutFilters() throws Exception {
            Mockito.when(findUserByIdUseCase.execute(any()))
                    .thenReturn(createMockUser());
            final DevProfile profile = createMockDevProfile();
            final PaginatedResult<DevProfile> result = new PaginatedResult<>(List.of(profile), 0, 5, 1L);

            Mockito.when(findAllDevProfileUseCase
                            .execute(Mockito.any(DevProfileFindAllQuery.class)))
                    .thenReturn(result);

            mockMvc.perform(get("/v1/dev-profiles"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].name", is("Gustavo Akira")))
                    .andExpect(jsonPath("$.totalElements", is(1)));
        }
    }

    @Nested
    class GetProfile{
        @Test
        void shouldGetProfileWhenLogged() throws Exception {
            Mockito.when(findUserByIdUseCase.execute(any()))
                    .thenReturn(createMockUser());
            final DevProfile profile = createMockDevProfile();

            Mockito.when(useCases.findDevProfileByIdUseCase().execute(new FindDevProfileByIdQuery(profile.getId()))).thenReturn(profile);
            mockMvc.perform(get("/v1/dev-profiles/profile"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Gustavo Akira")));
        }
    }



    private DevProfile createMockDevProfile() throws BusinessException {
        return new DevProfile(
                1L,
                1L,
                "Gustavo Akira",
                "gustavo@email.com",
                "123456",
                "Java developer",
                new Address("Rua X", "São Paulo", "SP", "BR",  "01234-567"),
                "https://github.com/gustavo-akira",
                "https://linkedin.com/in/gustavo",
                List.of("Java", "Spring Boot"),
                true
        );
    }
}
