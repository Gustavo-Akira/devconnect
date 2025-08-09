package br.com.gustavoakira.devconnect.adapters.inbound.controller.project.dto;

import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequest {
    @NotEmpty
    @NotNull
    private String name;
    @NotEmpty
    @NotNull
    private String description;
    @NotEmpty
    @NotNull
    private String repoUrl;

    public Project toDomain(Long devProfile) throws BusinessException {
        return new Project(
                name,
                description,
                repoUrl,
                devProfile
        );
    }
}
