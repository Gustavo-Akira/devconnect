package br.com.gustavoakira.devconnect.adapters.inbound.controller.passwordrecovery;

import br.com.gustavoakira.devconnect.adapters.config.JwtProvider;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.passwordrecovery.dto.RequestPasswordRecoveryRequest;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.RequestPasswordRecoveryUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = PasswordRecoveryController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
@ExtendWith(MockitoExtension.class)
class PasswordRecoveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RequestPasswordRecoveryUseCase requestPasswordRecoveryUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Test
    void shouldReturn204AndCallUseCase() throws Exception {
        final RequestPasswordRecoveryRequest request =
                new RequestPasswordRecoveryRequest("gustavo@email.com");

        mockMvc.perform(post("/v1/password-recovery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        Mockito.verify(requestPasswordRecoveryUseCase)
                .execute(any());
    }

    @Test
    void shouldReturn400WhenEmailIsInvalid() throws Exception {
        final RequestPasswordRecoveryRequest request =
                new RequestPasswordRecoveryRequest("");

        mockMvc.perform(post("/v1/password-recovery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        Mockito.verify(requestPasswordRecoveryUseCase, Mockito.never())
                .execute(any());
    }
}
