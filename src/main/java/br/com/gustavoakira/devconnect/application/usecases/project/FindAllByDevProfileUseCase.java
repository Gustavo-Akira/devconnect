package br.com.gustavoakira.devconnect.application.usecases.project;

import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;

public interface FindAllByDevProfileUseCase {
    PaginatedResult<Project> execute(Long devProfileId, int size, int page) throws BusinessException;
}
