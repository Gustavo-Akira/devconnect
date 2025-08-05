package br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.ProjectEntity;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMapperTest {
    private final ProjectMapper projectMapper = new ProjectMapper();

    @Nested
    class toEntity{
        @Test
        void shouldReturnAnEntityWhenPassedAndProjectDomain() throws BusinessException {
            final Project project = new Project(1L,"Dev Connect","Dev connect is social media that connect devs","https://github.com/Gustavo-Akira/devconnect",2L);
            final ProjectEntity entity = projectMapper.toEntity(project);
            assertEquals(1L, entity.getId());
            assertEquals("Dev Connect", entity.getName());
            assertEquals("Dev connect is social media that connect devs",entity.getDescription());
            assertEquals("https://github.com/Gustavo-Akira/devconnect", entity.getRepoUrl());
            assertEquals(2L, entity.getDevProfileId());
        }
    }
    @Nested
    class toDomain{
        @Test
        void shouldReturnAnValidDomainWhenAllRequiredInformationAreValid() throws BusinessException {
            final ProjectEntity projectEntity = new ProjectEntity(1L,"Dev Connect","Dev connect is social media that connect devs","https://github.com/Gustavo-Akira/devconnect",2L);
            final Project domain = projectMapper.toDomain(projectEntity);
            assertEquals(1L, domain.getId());
            assertEquals("Dev Connect", domain.getName());
            assertEquals("Dev connect is social media that connect devs", domain.getDescription());
            assertEquals("https://github.com/Gustavo-Akira/devconnect", domain.getRepoUrl());
            assertEquals(2L, domain.getDevProfileId());
        }

        @Test
        void shouldThrownBusinessExceptionWhenAnRequiredInformationAreInvalid() throws BusinessException {
            final ProjectEntity projectEntity = new ProjectEntity(1L,"Dev Connect","Dev connect is social media that connect devs","https://something.com/Gustavo-Akira/devconnect",2L);
            assertThrows(BusinessException.class, ()->projectMapper.toDomain(projectEntity));
        }
    }
}