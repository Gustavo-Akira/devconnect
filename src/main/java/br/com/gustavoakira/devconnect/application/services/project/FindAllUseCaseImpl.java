package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.project.FindAllUseCase;
import br.com.gustavoakira.devconnect.application.usecases.project.response.ProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllUseCaseImpl implements FindAllUseCase {

    @Autowired
    private IProjectRepository repository;

    @Autowired
    private IDevProfileRepository devProfileRepository;

    @Override
    public PaginatedResult<ProjectResponse> execute(int size, int page) throws BusinessException {
        final PaginatedResult<Project> projectPaginatedResult = repository.findAllProject(page,size);
        final List<ProjectResponse> responseList = projectPaginatedResult.getContent().stream().map(project -> {
            try {
                final DevProfile profile = devProfileRepository.findById(project.getDevProfileId());
                return new ProjectResponse(project,profile);
            } catch (EntityNotFoundException | BusinessException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        return new PaginatedResult<>(responseList,projectPaginatedResult.getPage(),projectPaginatedResult.getSize(),projectPaginatedResult.getTotalElements());
    }


}
