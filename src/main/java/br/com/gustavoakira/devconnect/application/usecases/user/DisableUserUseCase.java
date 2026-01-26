package br.com.gustavoakira.devconnect.application.usecases.user;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.user.command.DisableUserCommand;

public interface DisableUserUseCase {
    void execute(DisableUserCommand command, Long loggedId) throws EntityNotFoundException, BusinessException;
}
