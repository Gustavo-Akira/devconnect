package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.publishers.CreateDevProfileEventPublisher;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.SaveDevProfileUseCase;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.SaveDevProfileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveDevProfileUseCaseService implements SaveDevProfileUseCase {


    private final IDevProfileRepository repository;
    private final CreateDevProfileEventPublisher publisher;

    public SaveDevProfileUseCaseService(IDevProfileRepository repository, CreateDevProfileEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public DevProfile execute(SaveDevProfileCommand command) throws BusinessException {
        final Address address = new Address(
                command.street(),
                command.city(),
                command.state(),
                command.country(),
                command.zipCode()
        );
        final DevProfile devProfile = new DevProfile(
                1L,
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
