package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.usecases.project.FindProjectByIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindProjectByIdUseCaseImpl implements FindProjectByIdUseCase {

    @Autowired
    private IProjectRepository repository;

    @Override
    public Project execute(Long id) throws BusinessException, EntityNotFoundException {
        return repository.findProjectById(id);
    }
}
