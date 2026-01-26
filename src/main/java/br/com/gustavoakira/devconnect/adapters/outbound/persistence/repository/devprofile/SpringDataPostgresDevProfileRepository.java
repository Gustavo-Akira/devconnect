package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataPostgresDevProfileRepository extends JpaRepository<DevProfileEntity, Long> {
}
