package br.com.gustavoakira.devconnect.application.services.user;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.services.devprofile.FindDevProfileByIdUseCaseImpl;
import br.com.gustavoakira.devconnect.application.usecases.user.FindUserByIdUseCase;
import br.com.gustavoakira.devconnect.application.usecases.user.query.FindUserByIdQuery;
import org.springframework.stereotype.Service;

@Service
public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    private final IUserRepository repository;

    public FindUserByIdUseCaseImpl(IUserRepository repository){
        this.repository = repository;
    }

    @Override
    public User execute(FindUserByIdQuery query) throws BusinessException, EntityNotFoundException {
        return repository.findById(query.id());
    }
}
