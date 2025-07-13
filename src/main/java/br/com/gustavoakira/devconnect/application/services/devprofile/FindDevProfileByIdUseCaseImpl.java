package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.FindDevProfileByIdUseCase;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.query.FindDevProfileByIdQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindDevProfileByIdUseCaseImpl implements FindDevProfileByIdUseCase {

    @Autowired
    private IDevProfileRepository repository;

    @Override
    public DevProfile execute(FindDevProfileByIdQuery query) throws EntityNotFoundException, BusinessException {
        return repository.findById(query.getId());
    }
}
