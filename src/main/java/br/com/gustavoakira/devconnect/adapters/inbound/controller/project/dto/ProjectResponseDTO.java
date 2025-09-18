package br.com.gustavoakira.devconnect.adapters.inbound.controller.project.dto;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.usecases.project.response.ProjectResponse;
import lombok.Data;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String repoUrl;
    private Owner owner;
    @Data
    class Owner{
        private Long id;
        private String name;

        public Owner(Long id, String name){
            this.id = id;
            this.name = name;
        }
    }

    public ProjectResponseDTO(){

    }

    public ProjectResponseDTO(ProjectResponse response){
        this.id = response.getId();
        this.name = response.getName();
        this.description = response.getDescription();
        this.repoUrl = response.getRepoUrl();
        this.owner = new Owner(response.getOwnerId(),response.getOwnerName());
    }
}

