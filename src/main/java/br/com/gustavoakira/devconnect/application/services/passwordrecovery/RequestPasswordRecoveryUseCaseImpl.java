package br.com.gustavoakira.devconnect.application.services.passwordrecovery;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IPasswordRecoveryRepository;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.RequestPasswordRecoveryUseCase;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.command.RequestPasswordRecoveryCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
public class RequestPasswordRecoveryUseCaseImpl implements RequestPasswordRecoveryUseCase {

    private final Logger logger = LoggerFactory.getLogger(RequestPasswordRecoveryUseCaseImpl.class);

    private final IPasswordRecoveryRepository repository;

    private final IUserRepository userRepository;

    public RequestPasswordRecoveryUseCaseImpl(IPasswordRecoveryRepository repository, IUserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(RequestPasswordRecoveryCommand command) {
        try {
            final Long userId = userRepository.findByEmail(command.email()).getId();
            repository.invalidateAllByUserId(userId);
            final PasswordRecovery passwordRecovery = new PasswordRecovery(null,generateSecureToken(),userId, Instant.now().plusSeconds(300));
            repository.save(passwordRecovery);
        } catch (EntityNotFoundException e) {
            logger.info("Password Recovery for not existing email {}", command.email());
        }catch (BusinessException ex){
            logger.error("Password Recovery with non valid return from database for email {}", command.email(),ex);
        }
    }

    private String generateSecureToken(){
        final SecureRandom random = new SecureRandom();
        final byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
