package br.com.gustavoakira.devconnect.application.usecases.user;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.user.query.FindUserByIdQuery;

public interface FindUserByIdUseCase {
    User execute(FindUserByIdQuery query) throws BusinessException, EntityNotFoundException;
}
