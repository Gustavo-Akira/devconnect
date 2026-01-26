package br.com.gustavoakira.devconnect.application.services.user;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.ForbiddenException;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.user.DisableUserUseCase;
import br.com.gustavoakira.devconnect.application.usecases.user.command.DisableUserCommand;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class DisableUserUseCaseImpl implements DisableUserUseCase {

    private final IUserRepository repository;

    public DisableUserUseCaseImpl(IUserRepository repository){
        this.repository = repository;
    }

    @Override
    public void execute(DisableUserCommand command, Long loggedId) throws EntityNotFoundException, BusinessException {
        if(!Objects.equals(command.id(), loggedId)){
            throw new ForbiddenException("Unauthorized");
        }
        final User user = this.repository.findById(command.id());
        user.disable();
        this.repository.save(user);
    }
}
