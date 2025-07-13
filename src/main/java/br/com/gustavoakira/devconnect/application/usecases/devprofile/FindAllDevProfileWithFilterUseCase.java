package br.com.gustavoakira.devconnect.application.usecases.devprofile;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.filters.DevProfileFilter;

public interface FindAllDevProfileWithFilterUseCase {
    PaginatedResult<DevProfile> execute(DevProfileFilter filter,int page, int size) throws BusinessException;
}
