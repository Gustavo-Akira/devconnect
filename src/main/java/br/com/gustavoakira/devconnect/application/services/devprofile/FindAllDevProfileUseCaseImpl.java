package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.FindAllDevProfileUseCase;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.query.DevProfileFindAllQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindAllDevProfileUseCaseImpl implements FindAllDevProfileUseCase {
    @Autowired
    private IDevProfileRepository repository;
    @Override
    public PaginatedResult<DevProfile> execute(DevProfileFindAllQuery query) throws BusinessException {
        return repository.findAll(query.getPage(),query.getSize());
    }
}
