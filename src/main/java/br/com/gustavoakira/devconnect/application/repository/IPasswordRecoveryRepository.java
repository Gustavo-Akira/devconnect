package br.com.gustavoakira.devconnect.application.repository;

import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

public interface IPasswordRecoveryRepository {
    PasswordRecovery save(PasswordRecovery recovery) throws BusinessException;

    PasswordRecovery findByToken(String token) throws BusinessException;

    void invalidateAllByUserId(Long userId);
}