package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

public class Project {
    private Long id;
    private String name;
    private String description;
    private String repoUrl;

    public Project(Long id, String name, String description, String repoUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.repoUrl = repoUrl;
    }

    public Project(String name, String description, String repoUrl) {
        this.name = name;
        this.description = description;
        this.repoUrl = repoUrl;
    }

    private void validate() throws BusinessException {
        if(!repoUrl.startsWith("https://github.com")){
            throw new BusinessException("The repo link have to be in github or similars");
        }
    }
}
