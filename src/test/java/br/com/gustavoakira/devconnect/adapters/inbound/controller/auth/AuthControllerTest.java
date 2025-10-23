package br.com.gustavoakira.devconnect.adapters.inbound.controller.auth;

import br.com.gustavoakira.devconnect.adapters.config.JwtProvider;
import br.com.gustavoakira.devconnect.adapters.config.SecurityConfig;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.auth.dto.TokenRequest;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.auth.TokenGrantUseCase;

import br.com.gustavoakira.devconnect.application.usecases.auth.response.TokenGrantResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
@Import(SecurityConfig.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TokenGrantUseCase tokenGrantUseCase;

    @MockitoBean
    private JwtProvider provider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnTokenResponseWhenLoginIsSuccessful() throws Exception {
        final TokenRequest tokenRequest = new TokenRequest("password","user@example.com", "password123");

        final TokenGrantResponse tokenGrantResponse = new TokenGrantResponse("fake-jwt-token", 3600L);
        Mockito.when(tokenGrantUseCase.execute(any())).thenReturn(tokenGrantResponse);

        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenRequest)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("jwt"))
                .andExpect(jsonPath("expiresIn").exists())
                .andExpect(jsonPath("token").exists());
    }

    @Test
    void shouldReturnLogoutWithSuccessWhenLogout() throws Exception {
        mockMvc.perform(post("/v1/auth/logout")
                .cookie(new Cookie("jwt","teste")))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("jwt"))
                .andExpect(cookie().maxAge("jwt",0))
        ;
    }
}