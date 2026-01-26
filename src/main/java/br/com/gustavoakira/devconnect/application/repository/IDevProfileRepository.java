package br.com.gustavoakira.devconnect.application.repository;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.filters.DevProfileFilter;

public interface IDevProfileRepository {
    DevProfile findById(Long id) throws EntityNotFoundException, BusinessException;
    DevProfile save(DevProfile profile) throws BusinessException;
    DevProfile update(DevProfile profile) throws BusinessException;
    PaginatedResult<DevProfile> findAll(int page,int size) throws BusinessException;
    PaginatedResult<DevProfile> findAllWithFilter(DevProfileFilter filter, int page, int size) throws BusinessException;
}
