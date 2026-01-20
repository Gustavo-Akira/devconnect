package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.user;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataPostgresUserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByEmail(String email);
}
