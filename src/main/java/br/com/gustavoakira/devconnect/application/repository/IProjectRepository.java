package br.com.gustavoakira.devconnect.application.repository;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;

public interface IProjectRepository {
    Project findProjectById(Long id) throws EntityNotFoundException, BusinessException;
    PaginatedResult<Project> findAllProjectByDevId(Long id, int page,int size) throws BusinessException;
    PaginatedResult<Project> findAllProject(int page,int size) throws BusinessException;
    Project createProject(Project project) throws BusinessException;
    void deleteProject(Long id) throws EntityNotFoundException;
    Project updateProject(Project project) throws EntityNotFoundException, BusinessException;
}
