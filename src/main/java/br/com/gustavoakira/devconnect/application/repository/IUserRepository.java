package br.com.gustavoakira.devconnect.application.repository;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

public interface IUserRepository {
    User findById(Long id) throws BusinessException, EntityNotFoundException;
    User findByEmail(String email) throws EntityNotFoundException, BusinessException;
    User save(User user) throws BusinessException;
}
