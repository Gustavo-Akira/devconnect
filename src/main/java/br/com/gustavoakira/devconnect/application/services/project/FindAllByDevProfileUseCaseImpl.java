package br.com.gustavoakira.devconnect.application.services.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.project.FindAllByDevProfileUseCase;
import br.com.gustavoakira.devconnect.application.usecases.project.response.ProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindAllByDevProfileUseCaseImpl implements FindAllByDevProfileUseCase {

    @Autowired
    private IProjectRepository repository;

    @Autowired
    private IDevProfileRepository devProfileRepository;

    @Override
    public PaginatedResult<ProjectResponse> execute(Long devProfileId, int size, int page) throws BusinessException, EntityNotFoundException {

        final PaginatedResult<Project> projectPaginatedResult =  repository.findAllProjectByDevId(devProfileId,page,size);
        final List<ProjectResponse> responseList = new ArrayList<>();
        for(Project project: projectPaginatedResult.getContent()){
            final DevProfile profile = devProfileRepository.findById(project.getDevProfileId());
            responseList.add(new ProjectResponse(project,profile));
        }
        return new PaginatedResult<>(responseList,projectPaginatedResult.getPage(),projectPaginatedResult.getSize(),projectPaginatedResult.getTotalElements());
    }
}
