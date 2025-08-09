package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.usecases.project.DeleteProjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProjectUseCaseImpl implements DeleteProjectUseCase {

    @Autowired
    private IProjectRepository repository;

    @Override
    public void execute(Long id) throws EntityNotFoundException {
        repository.deleteProject(id);
    }
}
