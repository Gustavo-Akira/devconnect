package br.com.gustavoakira.devconnect.application.usecases.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.SaveDevProfileCommand;

public interface SaveDevProfileUseCase {
    DevProfile execute(SaveDevProfileCommand command) throws BusinessException;
}
