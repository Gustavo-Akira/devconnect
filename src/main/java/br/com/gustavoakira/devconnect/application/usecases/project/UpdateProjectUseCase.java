package br.com.gustavoakira.devconnect.application.usecases.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

public interface UpdateProjectUseCase {
    Project execute(Project project) throws BusinessException, EntityNotFoundException;
}
