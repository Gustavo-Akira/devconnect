package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.DeleteDevProfileUseCase;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.DeleteDevProfileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteDevProfileUseCaseImpl implements DeleteDevProfileUseCase {

    @Autowired
    private IDevProfileRepository repository;

    @Override
    public void execute(DeleteDevProfileCommand command) throws EntityNotFoundException {
       repository.deleteProfile(command.getId());
    }
}
