package br.com.gustavoakira.devconnect.application.usecases.auth;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.auth.command.TokenGrantCommand;
import br.com.gustavoakira.devconnect.application.usecases.auth.response.TokenGrantResponse;

public interface TokenGrantUseCase {
    TokenGrantResponse execute(TokenGrantCommand command) throws BusinessException, EntityNotFoundException;
}