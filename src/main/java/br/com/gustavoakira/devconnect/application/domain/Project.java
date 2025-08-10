package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

import java.util.Objects;

public class Project {
    private Long id;
    private String name;
    private String description;
    private String repoUrl;
    private Long devProfileId;

    public Project(Long id, String name, String description, String repoUrl, Long devProfileId) throws BusinessException {
        this(name,description,repoUrl,devProfileId);
        this.id = id;
    }

    public Project(String name, String description, String repoUrl, Long devProfileId) throws BusinessException {
        this.name = name;
        this.description = description;
        this.repoUrl = repoUrl;
        this.devProfileId= devProfileId;
        validate();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getDevProfileId() {
        return devProfileId;
    }

    public String getDescription() {
        return description;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    private void validate() throws BusinessException {
        if(name.strip().length() < 5){
            throw new BusinessException("The project name have to be more than 5 characters");
        }
        if(!repoUrl.startsWith("https://github.com")){
            throw new BusinessException("The repo link have to be in github or similars");
        }
        if(devProfileId <= 0){
            throw new BusinessException("Dev profile id have to be 1 or more");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) && Objects.equals(name, project.name) && Objects.equals(description, project.description) && Objects.equals(repoUrl, project.repoUrl) && Objects.equals(devProfileId, project.devProfileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, repoUrl, devProfileId);
    }
}
