package br.com.gustavoakira.devconnect.application.usecases.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectUseCases {
    private final SaveProjectUseCase saveProjectUseCase;
    private final FindProjectByIdUseCase findProjectByIdUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;

    public ProjectUseCases(SaveProjectUseCase saveDevProfileUseCase, FindProjectByIdUseCase findProjectByIdUseCase, UpdateProjectUseCase updateProjectUseCase) {
        this.saveProjectUseCase = saveDevProfileUseCase;
        this.findProjectByIdUseCase = findProjectByIdUseCase;
        this.updateProjectUseCase = updateProjectUseCase;
    }

    public FindProjectByIdUseCase getFindProjectByIdUseCase() {
        return findProjectByIdUseCase;
    }

    public UpdateProjectUseCase getUpdateProjectUseCase() {
        return updateProjectUseCase;
    }

    public SaveProjectUseCase getSaveProjectUseCase() {
        return saveProjectUseCase;
    }
}
