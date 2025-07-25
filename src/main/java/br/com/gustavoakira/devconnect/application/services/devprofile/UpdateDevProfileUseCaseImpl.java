package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.UpdateDevProfileUseCase;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.UpdateDevProfileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateDevProfileUseCaseImpl implements UpdateDevProfileUseCase {
    @Autowired
    private IDevProfileRepository repository;
    @Override
    public DevProfile execute(UpdateDevProfileCommand command) throws EntityNotFoundException, BusinessException {
        Address address = new Address(
                command.street(),
                command.city(),
                command.state(),
                command.country(),
                command.zipCode()
        );
        DevProfile devProfile = new DevProfile(
                command.id(),
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
        return repository.update(devProfile);
    }
}
