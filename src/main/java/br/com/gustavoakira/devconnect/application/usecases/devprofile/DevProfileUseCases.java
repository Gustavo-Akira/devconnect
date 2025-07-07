package br.com.gustavoakira.devconnect.application.usecases.devprofile;

import org.springframework.stereotype.Component;

@Component
public class DevProfileUseCases {
    private final SaveDevProfileUseCase saveDevProfileUseCase;


    public DevProfileUseCases(SaveDevProfileUseCase saveDevProfileUseCase) {
        this.saveDevProfileUseCase = saveDevProfileUseCase;
    }

    public SaveDevProfileUseCase saveDevProfileUseCase() {
        return saveDevProfileUseCase;
    }
}
