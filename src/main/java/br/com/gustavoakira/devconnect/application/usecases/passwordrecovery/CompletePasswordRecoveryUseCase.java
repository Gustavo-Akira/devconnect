package br.com.gustavoakira.devconnect.application.usecases.passwordrecovery;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.command.CompletePasswordRecoveryCommand;

public interface CompletePasswordRecoveryUseCase {
    PasswordRecovery execute(CompletePasswordRecoveryCommand command) throws BusinessException, EntityNotFoundException;
}
