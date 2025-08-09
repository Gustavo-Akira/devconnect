package br.com.gustavoakira.devconnect.application.usecases.project;

import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;

public interface FindAllUseCase {
    PaginatedResult<Project> execute(int size, int page) throws BusinessException;
}
