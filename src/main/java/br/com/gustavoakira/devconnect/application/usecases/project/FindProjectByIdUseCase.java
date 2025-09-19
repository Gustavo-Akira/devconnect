package br.com.gustavoakira.devconnect.application.usecases.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.project.response.ProjectResponse;

public interface FindProjectByIdUseCase {
    ProjectResponse execute(Long id) throws BusinessException, EntityNotFoundException;
}
