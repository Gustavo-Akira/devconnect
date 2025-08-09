package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.usecases.project.UpdateProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectUseCaseImpl implements UpdateProjectUseCase {

    @Autowired
    private IProjectRepository projectRepository;

    @Override
    public Project execute(Project project) throws BusinessException, EntityNotFoundException {
        return projectRepository.updateProject(project);
    }
}
