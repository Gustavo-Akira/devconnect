package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.project.FindAllByDevProfileUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindAllByDevProfileUseCaseImpl implements FindAllByDevProfileUseCase {

    @Autowired
    private IProjectRepository repository;

    @Override
    public PaginatedResult<Project> execute(Long devProfileId, int size, int page) throws BusinessException {
        return repository.findAllProjectByDevId(devProfileId,page,size);
    }
}
