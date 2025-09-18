package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.usecases.project.FindProjectByIdUseCase;
import br.com.gustavoakira.devconnect.application.usecases.project.response.ProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindProjectByIdUseCaseImpl implements FindProjectByIdUseCase {

    @Autowired
    private IProjectRepository repository;

    @Autowired
    private IDevProfileRepository devProfileRepository;

    @Override
    public ProjectResponse execute(Long id) throws BusinessException, EntityNotFoundException {
        final Project project = repository.findProjectById(id);
        final DevProfile profile = devProfileRepository.findById(project.getDevProfileId());
        return new ProjectResponse(project,profile);
    }
}
