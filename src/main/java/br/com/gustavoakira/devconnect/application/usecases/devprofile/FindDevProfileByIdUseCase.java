package br.com.gustavoakira.devconnect.application.usecases.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.query.FindDevProfileByIdQuery;

public interface FindDevProfileByIdUseCase {
    DevProfile execute(FindDevProfileByIdQuery query) throws EntityNotFoundException, BusinessException;
}
