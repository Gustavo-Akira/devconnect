package br.com.gustavoakira.devconnect.application.usecases.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectUseCases {
    private final SaveProjectUseCase saveProjectUseCase;

    public ProjectUseCases(SaveProjectUseCase saveDevProfileUseCase) {
        this.saveProjectUseCase = saveDevProfileUseCase;
    }

    public SaveProjectUseCase getSaveProjectUseCase() {
        return saveProjectUseCase;
    }
}
