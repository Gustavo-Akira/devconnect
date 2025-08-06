package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.usecases.project.SaveProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveProjectUseCaseImpl implements SaveProjectUseCase {

    @Autowired
    private IProjectRepository repository;

    @Override
    public Project execute(Project project) throws BusinessException {
        return repository.createProject(project);
    }
}
