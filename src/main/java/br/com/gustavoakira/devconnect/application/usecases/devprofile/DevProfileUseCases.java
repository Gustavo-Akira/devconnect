package br.com.gustavoakira.devconnect.application.usecases.devprofile;

import org.springframework.stereotype.Component;

@Component
public class DevProfileUseCases {
    private final SaveDevProfileUseCase saveDevProfileUseCase;
    private final FindAllDevProfileUseCase findAllDevProfileUseCase;
    private final FindAllDevProfileWithFilterUseCase findAllDevProfileWithFilterUseCase;
    private final DeleteDevProfileUseCase deleteDevProfileUseCase;

    public DevProfileUseCases(SaveDevProfileUseCase saveDevProfileUseCase, FindAllDevProfileUseCase findAllDevProfileUseCase, FindAllDevProfileWithFilterUseCase findAllDevProfileWithFilterUseCase, DeleteDevProfileUseCase deleteDevProfileUseCase) {
        this.saveDevProfileUseCase = saveDevProfileUseCase;
        this.findAllDevProfileUseCase = findAllDevProfileUseCase;
        this.findAllDevProfileWithFilterUseCase = findAllDevProfileWithFilterUseCase;
        this.deleteDevProfileUseCase = deleteDevProfileUseCase;
    }

    public SaveDevProfileUseCase saveDevProfileUseCase() {
        return saveDevProfileUseCase;
    }

    public FindAllDevProfileUseCase findAllDevProfileUseCase(){
        return findAllDevProfileUseCase;
    }

    public FindAllDevProfileWithFilterUseCase findAllDevProfileWithFilterUseCase() {
        return findAllDevProfileWithFilterUseCase;
    }

    public DeleteDevProfileUseCase deleteDevProfileUseCase(){
        return deleteDevProfileUseCase;
    }
}
