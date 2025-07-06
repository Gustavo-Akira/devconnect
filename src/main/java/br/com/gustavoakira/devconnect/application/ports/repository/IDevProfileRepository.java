package br.com.gustavoakira.devconnect.application.ports.repository;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;

public interface IDevProfileRepository {
    DevProfile findById(Long id) throws EntityNotFoundException, BusinessException;
    DevProfile save(DevProfile profile) throws BusinessException;
    DevProfile update(DevProfile profile) throws BusinessException;
    void deleteProfile(Long id) throws EntityNotFoundException;
    PaginatedResult<DevProfile> findAll(int page,int size) throws BusinessException;
}
