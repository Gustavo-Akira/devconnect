package br.com.gustavoakira.devconnect.application.services.passwordrecovery;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IPasswordRecoveryRepository;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.CompletePasswordRecoveryUseCase;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.command.CompletePasswordRecoveryCommand;

import java.time.Instant;

public class CompletePasswordRecoveryUseCaseImpl implements CompletePasswordRecoveryUseCase {

    private final IPasswordRecoveryRepository repository;
    private final IUserRepository userRepository;

    public CompletePasswordRecoveryUseCaseImpl(IPasswordRecoveryRepository repository, IUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public PasswordRecovery execute(CompletePasswordRecoveryCommand command) throws BusinessException, EntityNotFoundException {
        PasswordRecovery recovery = repository.findByToken(command.token());
        User user = userRepository.findById(recovery.getUserId());
        user.changePassword(command.newPassword());
        recovery.markAsUsed(Instant.now());
        userRepository.save(user);
        return repository.save(recovery);
    }
}
