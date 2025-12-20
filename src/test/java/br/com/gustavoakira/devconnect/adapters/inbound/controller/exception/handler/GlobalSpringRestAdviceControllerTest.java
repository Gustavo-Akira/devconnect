package br.com.gustavoakira.devconnect.adapters.inbound.controller.exception.handler;

import br.com.gustavoakira.devconnect.adapters.config.JwtFilter;
import br.com.gustavoakira.devconnect.adapters.config.JwtProvider;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = {TestController.class},excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(GlobalSpringRestAdviceController.class)
class GlobalSpringRestAdviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtProvider jwtProvider;


    @Test
    void whenEntityNotFoundThenReturns404() throws Exception {
        mockMvc.perform(get("/test-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("ENTITY_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Resource not found"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void whenGenericExceptionThenReturns500() throws Exception {
        mockMvc.perform(get("/test-generic-exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.message").value("Generic Exception"))
                .andExpect(jsonPath("$.status").value(500));
    }
    @Test
    void whenBusinessExceptionThenReturns400() throws Exception {
        mockMvc.perform(get("/test-business-exception"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BUSINESS_VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("Business Exception"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void whenForbiddenExceptionThenReturns403() throws Exception {
        mockMvc.perform(get("/test-forbidden-exception"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("AUTH_ERROR"))
                .andExpect(jsonPath("$.message").value("Forbidden Exception"))
                .andExpect(jsonPath("$.status").value(403));
    }

    @Test
    void whenValidationFailsThenReturns400() throws Exception {
        mockMvc.perform(post("/test-validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.errorDetailList[0].field").value("name"));
    }
}