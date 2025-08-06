package br.com.gustavoakira.devconnect.application.usecases.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

public interface FindProjectByIdUseCase {
    Project execute(Long id) throws BusinessException, EntityNotFoundException;
}
