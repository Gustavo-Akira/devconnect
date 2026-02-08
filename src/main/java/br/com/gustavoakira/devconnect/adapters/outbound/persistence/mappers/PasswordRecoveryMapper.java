package br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.PasswordRecoveryEntity;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class PasswordRecoveryMapper {
    public PasswordRecoveryEntity toEntity(PasswordRecovery recovery){
        if(recovery == null){
            return null;
        }
        final PasswordRecoveryEntity entity = new PasswordRecoveryEntity();
        entity.setId(recovery.getId());
        entity.setToken(recovery.getToken());
        entity.setUsedAt(recovery.getUsedAt());
        entity.setExpiresAt(recovery.getExpiresAt());
        entity.setUserId(recovery.getUserId());
        return entity;
    }

    public PasswordRecovery toDomain(PasswordRecoveryEntity entity) throws BusinessException {
        if(entity == null){
            return null;
        }
        final  PasswordRecovery recovery = new PasswordRecovery(entity.getId(), entity.getToken(), entity.getUserId(), entity.getExpiresAt());
        if(entity.getUsedAt() != null) {
            recovery.markAsUsed(entity.getUsedAt());
        }
        return recovery;
    }
}
