package br.com.gustavoakira.devconnect.application.usecases.project;

import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

public interface SaveProjectUseCase {
    Project execute(Project project) throws BusinessException;
}
