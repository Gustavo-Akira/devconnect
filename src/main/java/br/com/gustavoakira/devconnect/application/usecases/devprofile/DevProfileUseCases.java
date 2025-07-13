package br.com.gustavoakira.devconnect.application.usecases.devprofile;

import org.springframework.stereotype.Component;

@Component
public class DevProfileUseCases {
    private final SaveDevProfileUseCase saveDevProfileUseCase;
    private final FindAllDevProfileUseCase findAllDevProfileUseCase;
    private final FindAllDevProfileWithFilterUseCase findAllDevProfileWithFilterUseCase;
    private final DeleteDevProfileUseCase deleteDevProfileUseCase;
    private final FindDevProfileByIdUseCase findDevProfileByIdUseCase;
    private final UpdateDevProfileUseCase updateDevProfileUseCase;

    public DevProfileUseCases(SaveDevProfileUseCase saveDevProfileUseCase, FindAllDevProfileUseCase findAllDevProfileUseCase, FindAllDevProfileWithFilterUseCase findAllDevProfileWithFilterUseCase, DeleteDevProfileUseCase deleteDevProfileUseCase, FindDevProfileByIdUseCase findDevProfileByIdUseCase, UpdateDevProfileUseCase updateDevProfileUseCase) {
        this.saveDevProfileUseCase = saveDevProfileUseCase;
        this.findAllDevProfileUseCase = findAllDevProfileUseCase;
        this.findAllDevProfileWithFilterUseCase = findAllDevProfileWithFilterUseCase;
        this.deleteDevProfileUseCase = deleteDevProfileUseCase;
        this.findDevProfileByIdUseCase = findDevProfileByIdUseCase;
        this.updateDevProfileUseCase = updateDevProfileUseCase;
    }

    public SaveDevProfileUseCase saveDevProfileUseCase() {
        return saveDevProfileUseCase;
    }

    public FindDevProfileByIdUseCase findDevProfileByIdUseCase() {
        return findDevProfileByIdUseCase;
    }

    public FindAllDevProfileUseCase findAllDevProfileUseCase(){
        return findAllDevProfileUseCase;
    }

    public FindAllDevProfileWithFilterUseCase findAllDevProfileWithFilterUseCase() {
        return findAllDevProfileWithFilterUseCase;
    }

    public UpdateDevProfileUseCase updateDevProfileUseCase(){
        return updateDevProfileUseCase;
    }

    public DeleteDevProfileUseCase deleteDevProfileUseCase(){
        return deleteDevProfileUseCase;
    }
}
