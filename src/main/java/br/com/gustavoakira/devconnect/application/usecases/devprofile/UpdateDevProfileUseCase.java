package br.com.gustavoakira.devconnect.application.usecases.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.UpdateDevProfileCommand;

public interface UpdateDevProfileUseCase {
    DevProfile execute(UpdateDevProfileCommand command, Long loggedId) throws EntityNotFoundException, BusinessException;
}
