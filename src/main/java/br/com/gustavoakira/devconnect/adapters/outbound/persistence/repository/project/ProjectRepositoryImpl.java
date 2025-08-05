package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.ProjectEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers.ProjectMapper;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IProjectRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepositoryImpl implements IProjectRepository {

    @Autowired
    private SpringDataPostgresProjectRepository repository;
    private final ProjectMapper mapper = new ProjectMapper();

    @Override
    public Project findProjectById(Long id) throws EntityNotFoundException, BusinessException {
        return mapper.toDomain(repository.findById(id).orElseThrow(()->new EntityNotFoundException("Project not found with id "+id)));
    }

    @Override
    public PaginatedResult<Project> findAllProjectByDevId(Long id, int page, int size) throws BusinessException {
        final Page<ProjectEntity> entities = repository.findAllByDevProfileId(id, Pageable.ofSize(size).withPage(page));
        final List<Project> content = new ArrayList<>();
        for(ProjectEntity entity: entities){
            content.add(mapper.toDomain(entity));
        }
        return new PaginatedResult<>(
                content,
                page,
                size,
                entities.getTotalElements()
        );
    }

    @Override
    public PaginatedResult<Project> findAllProject(int page, int size) throws BusinessException {
        final Page<ProjectEntity> entities = repository.findAll(Pageable.ofSize(size).withPage(page));
        final List<Project> content = new ArrayList<>();
        for(ProjectEntity entity: entities){
            content.add(mapper.toDomain(entity));
        }
        return new PaginatedResult<>(
                content,
                page,
                size,
                entities.getTotalElements()
        );
    }

    @Override
    public Project createProject(Project project) throws BusinessException {
        return mapper.toDomain(repository.save(mapper.toEntity(project)));
    }

    @Override
    public void deleteProject(Long id) throws EntityNotFoundException {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException("Project not found with id "+id);
        }
        repository.deleteById(id);
    }

    @Override
    public Project updateProject(Project project) throws EntityNotFoundException, BusinessException {
        if(!repository.existsById(project.getId())){
            throw new EntityNotFoundException("Project not found with id "+project.getId());
        }
        return mapper.toDomain(repository.save(mapper.toEntity(project)));
    }
}
