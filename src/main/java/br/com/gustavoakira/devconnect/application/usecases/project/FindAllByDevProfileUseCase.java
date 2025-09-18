package br.com.gustavoakira.devconnect.application.usecases.project;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.project.response.ProjectResponse;

public interface FindAllByDevProfileUseCase {
    PaginatedResult<ProjectResponse> execute(Long devProfileId, int size, int page) throws BusinessException;
}
