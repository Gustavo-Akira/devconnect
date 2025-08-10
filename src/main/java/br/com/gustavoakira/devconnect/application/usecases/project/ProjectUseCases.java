package br.com.gustavoakira.devconnect.application.usecases.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectUseCases {
    private final SaveProjectUseCase saveProjectUseCase;
    private final FindProjectByIdUseCase findProjectByIdUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;
    private final FindAllByDevProfileUseCase findAllByDevProfileUseCase;
    private final FindAllUseCase findAllUseCase;

    public ProjectUseCases(SaveProjectUseCase saveDevProfileUseCase, FindProjectByIdUseCase findProjectByIdUseCase, UpdateProjectUseCase updateProjectUseCase, DeleteProjectUseCase deleteProjectUseCase, FindAllByDevProfileUseCase findAllByDevProfileUseCase, FindAllUseCase findAllUseCase) {
        this.saveProjectUseCase = saveDevProfileUseCase;
        this.findProjectByIdUseCase = findProjectByIdUseCase;
        this.updateProjectUseCase = updateProjectUseCase;
        this.deleteProjectUseCase = deleteProjectUseCase;
        this.findAllByDevProfileUseCase = findAllByDevProfileUseCase;
        this.findAllUseCase = findAllUseCase;
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

    public DeleteProjectUseCase getDeleteProjectUseCase() {
        return deleteProjectUseCase;
    }

    public FindAllByDevProfileUseCase getFindAllByDevProfileUseCase() {
        return findAllByDevProfileUseCase;
    }

    public FindAllUseCase getFindAllUseCase() {
        return findAllUseCase;
    }
}
