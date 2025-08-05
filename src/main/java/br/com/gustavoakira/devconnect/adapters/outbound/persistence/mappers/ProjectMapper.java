package br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.ProjectEntity;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

public class ProjectMapper {
    public Project toDomain(ProjectEntity entity) throws BusinessException {
        return new Project(
          entity.getId(),
          entity.getName(),
          entity.getDescription(),
          entity.getRepoUrl(),
          entity.getDevProfileId()
        );
    }

    public ProjectEntity toEntity(Project project){
        return new ProjectEntity(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getRepoUrl(),
                project.getDevProfileId()
        );
    }
}
