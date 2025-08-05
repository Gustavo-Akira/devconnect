package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.project;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPostgresProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Page<ProjectEntity> findAllByDevProfileId(Long devProfileId, Pageable pageable);
}
