package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.ForbiddenException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.UpdateDevProfileUseCase;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.UpdateDevProfileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UpdateDevProfileUseCaseImpl implements UpdateDevProfileUseCase {
    @Autowired
    private IDevProfileRepository repository;
    @Override
    public DevProfile execute(UpdateDevProfileCommand command, Long loggedId) throws EntityNotFoundException, BusinessException {
        final DevProfile devProfile = repository.findById(command.id());
        if(!Objects.equals(devProfile.getUserId(), loggedId)){
            throw new ForbiddenException("Unauthorized action");
        }
        final Address address = new Address(
                command.street(),
                command.city(),
                command.state(),
                command.country(),
                command.zipCode()
        );
        devProfile.rename(command.name());
        devProfile.moveToNewAddress(address);
        devProfile.updateBio(command.bio());
        devProfile.updateGithubLink(command.githubLink());
        devProfile.updateLinkedinLink(command.linkedinLink());
        devProfile.updateStack(command.stack());
        return repository.update(devProfile);
    }
}
