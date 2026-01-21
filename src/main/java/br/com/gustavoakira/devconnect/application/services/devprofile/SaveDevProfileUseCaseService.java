package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.publishers.CreateDevProfileEventPublisher;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.SaveDevProfileUseCase;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.SaveDevProfileCommand;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SaveDevProfileUseCaseService implements SaveDevProfileUseCase {


    private final IDevProfileRepository repository;
    private final CreateDevProfileEventPublisher publisher;
    private final IUserRepository userRepository;

    public SaveDevProfileUseCaseService(IDevProfileRepository repository, CreateDevProfileEventPublisher publisher, IUserRepository userRepository) {
        this.repository = repository;
        this.publisher = publisher;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public DevProfile execute(SaveDevProfileCommand command) throws BusinessException {
        final Address address = new Address(
                command.street(),
                command.city(),
                command.state(),
                command.country(),
                command.zipCode()
        );
        final User user = userRepository.save(new User(command.name(), command.password(), command.email(), true));

        final DevProfile devProfile = new DevProfile(
                user.getId(),
                command.name(),
                command.email(),
                command.password(),
                command.bio(),
                address,
                command.githubLink(),
                command.linkedinLink(),
                command.stack(),
                true
        );
        final DevProfile saved = repository.save(devProfile);
        publisher.sendMessage(saved);
        return saved;
    }
}
