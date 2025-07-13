package br.com.gustavoakira.devconnect.application.usecases.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.DeleteDevProfileCommand;

public interface DeleteDevProfileUseCase {
    void execute(DeleteDevProfileCommand command) throws EntityNotFoundException;
}
