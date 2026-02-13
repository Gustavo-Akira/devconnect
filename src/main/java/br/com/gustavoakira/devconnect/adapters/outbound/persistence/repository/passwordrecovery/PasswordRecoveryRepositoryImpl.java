package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.passwordrecovery;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.PasswordRecoveryEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers.PasswordRecoveryMapper;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IPasswordRecoveryRepository;

public class PasswordRecoveryRepositoryImpl implements IPasswordRecoveryRepository {

    private final SpringDataPostgresPasswordRecoveryRepository repository;
    private final PasswordRecoveryMapper mapper;

    public PasswordRecoveryRepositoryImpl(SpringDataPostgresPasswordRecoveryRepository repository, PasswordRecoveryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PasswordRecovery save(PasswordRecovery recovery) throws BusinessException {
        return mapper.toDomain(repository.save(mapper.toEntity(recovery)));
    }

    @Override
    public PasswordRecovery findByToken(String token) throws BusinessException {
        PasswordRecoveryEntity entity = repository.findByToken(token).orElseThrow();
        return mapper.toDomain(entity);
    }

    @Override
    public void invalidateAllByUserId(Long userId) {
        repository.deleteAllByUserId(userId);
    }
}
