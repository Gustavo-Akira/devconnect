package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.passwordrecovery;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.PasswordRecoveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataPostgresPasswordRecoveryRepository extends JpaRepository<PasswordRecoveryEntity,Long> {
    Optional<PasswordRecoveryEntity> findByToken(String token);
    void deleteAllByUserId(Long userId);
}
