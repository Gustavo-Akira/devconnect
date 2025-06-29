package br.com.gustavoakira.devconnect.application.ports.repository;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;

import java.util.Optional;

public interface IDevProfileRepository {
    Optional<DevProfile> findById(Long id);
    DevProfile save(DevProfile profile);
    DevProfile update(DevProfile profile);
    void deleteProfile(Long id);
    PaginatedResult<DevProfile> findAll(int page,int size);
}
