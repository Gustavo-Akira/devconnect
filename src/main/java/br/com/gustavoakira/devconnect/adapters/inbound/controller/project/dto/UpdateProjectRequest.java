package br.com.gustavoakira.devconnect.adapters.inbound.controller.project.dto;

import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProjectRequest {
    @NotNull
    private Long id;
    @NotEmpty
    @NotNull
    private String name;
    @NotEmpty
    @NotNull
    private String description;
    @NotEmpty
    @NotNull
    private String repoUrl;

    public Project toDomain(Long devId) throws BusinessException {
        return new Project(
          id,
          name,
          description,
          repoUrl,
          devId
        );
    }
}
