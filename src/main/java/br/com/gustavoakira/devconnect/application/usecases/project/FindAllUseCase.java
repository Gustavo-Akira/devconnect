package br.com.gustavoakira.devconnect.application.usecases.project;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.project.response.ProjectResponse;

public interface FindAllUseCase {
    PaginatedResult<ProjectResponse> execute(int size, int page) throws BusinessException;
}
