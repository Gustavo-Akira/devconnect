package br.com.gustavoakira.devconnect.application.usecases.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectUseCases {
    private final SaveProjectUseCase saveProjectUseCase;
    private final FindProjectByIdUseCase findProjectByIdUseCase;

    public ProjectUseCases(SaveProjectUseCase saveDevProfileUseCase, FindProjectByIdUseCase findProjectByIdUseCase) {
        this.saveProjectUseCase = saveDevProfileUseCase;
        this.findProjectByIdUseCase = findProjectByIdUseCase;
    }

    public FindProjectByIdUseCase getFindProjectByIdUseCase() {
        return findProjectByIdUseCase;
    }

    public SaveProjectUseCase getSaveProjectUseCase() {
        return saveProjectUseCase;
    }
}
