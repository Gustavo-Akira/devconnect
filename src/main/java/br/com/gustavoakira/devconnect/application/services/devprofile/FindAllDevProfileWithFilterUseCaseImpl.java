package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.FindAllDevProfileWithFilterUseCase;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.filters.DevProfileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindAllDevProfileWithFilterUseCaseImpl implements FindAllDevProfileWithFilterUseCase {

    @Autowired
    private IDevProfileRepository repository;

    @Override
    public PaginatedResult<DevProfile> execute(DevProfileFilter filter, int page, int size) throws BusinessException {
        return repository.findAllWithFilter(filter,page,size);
    }
}
