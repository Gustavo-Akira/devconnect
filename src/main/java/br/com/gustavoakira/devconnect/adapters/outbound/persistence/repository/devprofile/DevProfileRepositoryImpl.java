package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.ports.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DevProfileRepositoryImpl implements IDevProfileRepository {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository;


    @Override
    public Optional<DevProfile> findById(Long id) {
        return springDataPostgresDevProfileRepository.findById(id).map(devProfileEntity -> mapper.map(devProfileEntity, DevProfile.class));
    }

    @Override
    public DevProfile save(DevProfile profile) {
        DevProfileEntity entity = mapToEntity(profile);
        return mapToDomain(springDataPostgresDevProfileRepository.save(entity));
    }



    @Override
    public DevProfile update(DevProfile profile) {
        DevProfileEntity entity = mapToEntity(profile);
        return mapToDomain(springDataPostgresDevProfileRepository.save(entity));
    }

    @Override
    public void deleteProfile(Long id) {
        DevProfileEntity toDelete = springDataPostgresDevProfileRepository.findById(id).orElseThrow();
        toDelete.setIsActive(false);
        springDataPostgresDevProfileRepository.save(toDelete);
    }

    @Override
    public PaginatedResult<DevProfile> findAll(int page, int size) {
        Page<DevProfile> devProfileEntities = springDataPostgresDevProfileRepository.findAll(Pageable.ofSize(page).withPage(page)).map(this::mapToDomain);
        return new PaginatedResult<>(
                devProfileEntities.getContent(),
                devProfileEntities.getPageable().getPageNumber(),
                devProfileEntities.getPageable().getPageSize(),
                devProfileEntities.getTotalElements()
        );
    }



    private DevProfileEntity mapToEntity(DevProfile profile) {
        return mapper.map(profile, DevProfileEntity.class);
    }

    private DevProfile mapToDomain(DevProfileEntity entity){
        return mapper.map(entity, DevProfile.class);
    }
}
