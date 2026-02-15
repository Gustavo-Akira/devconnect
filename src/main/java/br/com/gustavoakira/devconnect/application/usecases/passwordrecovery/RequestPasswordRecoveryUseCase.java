package br.com.gustavoakira.devconnect.application.usecases.passwordrecovery;

import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.command.RequestPasswordRecoveryCommand;

public interface RequestPasswordRecoveryUseCase {
    void execute(RequestPasswordRecoveryCommand command);
}
