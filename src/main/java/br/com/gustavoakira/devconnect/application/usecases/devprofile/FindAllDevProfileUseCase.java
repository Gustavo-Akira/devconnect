package br.com.gustavoakira.devconnect.application.usecases.devprofile;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.query.DevProfileFindAllQuery;

public interface FindAllDevProfileUseCase {
    PaginatedResult<DevProfile> execute(DevProfileFindAllQuery query) throws BusinessException;
}
