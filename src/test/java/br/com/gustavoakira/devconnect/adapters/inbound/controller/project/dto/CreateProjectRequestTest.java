package br.com.gustavoakira.devconnect.adapters.inbound.controller.project.dto;

import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateProjectRequestTest {
    @Test
    void shouldCreateDomainAndTransformToDomain() throws BusinessException {
        final CreateProjectRequest projectRequest = new CreateProjectRequest("akira","asdfdasfdsafdsaffds","https://github.com");
        final Project project = projectRequest.toDomain(1L);
        assertEquals(projectRequest.getName(), project.getName());
        assertEquals(projectRequest.getDescription(), project.getDescription());
        assertEquals(projectRequest.getRepoUrl(), project.getRepoUrl());
        assertEquals(1L, project.getDevProfileId());
    }

}