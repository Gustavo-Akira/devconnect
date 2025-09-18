package br.com.gustavoakira.devconnect.application.usecases.project.response;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.Project;

public class ProjectResponse {
    private Long id;
    private String name;
    private String repoUrl;
    private String description;
    private String ownerName;
    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public ProjectResponse(){

    }

    public ProjectResponse(Project project, DevProfile dev){
        this.id= project.getId();
        this.description = project.getDescription();
        this.name = project.getName();
        this.repoUrl = project.getRepoUrl();
        this.ownerId = dev.getId();
        this.ownerName = dev.getName();
    }
}
