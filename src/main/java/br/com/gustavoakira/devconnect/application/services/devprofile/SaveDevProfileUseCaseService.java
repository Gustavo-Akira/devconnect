package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.SaveDevProfileUseCase;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.SaveDevProfileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveDevProfileUseCaseService implements SaveDevProfileUseCase {

    @Autowired
    private IDevProfileRepository repository;

    @Override
    public DevProfile execute(SaveDevProfileCommand command) throws BusinessException {
        Address address = new Address(
                command.street(),
                command.city(),
                command.state(),
                command.country(),
                command.zipCode()
        );
        DevProfile devProfile = new DevProfile(
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
        return repository.save(devProfile);
    }
}
