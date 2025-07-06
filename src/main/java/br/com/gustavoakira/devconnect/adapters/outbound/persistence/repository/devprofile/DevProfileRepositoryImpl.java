package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers.DevProfileMapper;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.ports.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DevProfileRepositoryImpl implements IDevProfileRepository {

    private final DevProfileMapper mapper;

    private final SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository;

    public DevProfileRepositoryImpl(SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository, DevProfileMapper mapper) {
        this.springDataPostgresDevProfileRepository = springDataPostgresDevProfileRepository;
        this.mapper = mapper;
    }


    @Override
    public DevProfile findById(Long id) throws EntityNotFoundException, BusinessException {
        return mapper.toDomain(springDataPostgresDevProfileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("DevProfile not found with id: " + id)));
    }

    @Override
    public DevProfile save(DevProfile profile) throws BusinessException {
        DevProfileEntity entity = mapper.toEntity(profile);
        return mapper.toDomain(springDataPostgresDevProfileRepository.save(entity));
    }



    @Override
    public DevProfile update(DevProfile profile) throws BusinessException {
        DevProfileEntity entity = mapper.toEntity(profile);
        return mapper.toDomain(springDataPostgresDevProfileRepository.save(entity));
    }

    @Override
    public void deleteProfile(Long id) throws EntityNotFoundException {
        DevProfileEntity toDelete = springDataPostgresDevProfileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("DevProfile not found with id: " + id));
        toDelete.setIsActive(false);
        springDataPostgresDevProfileRepository.save(toDelete);
    }


    @Override
    public PaginatedResult<DevProfile> findAll(int page, int size) throws BusinessException {
        Page<DevProfileEntity> devProfileEntities = springDataPostgresDevProfileRepository.findAll(Pageable.ofSize(size).withPage(page));
        List<DevProfile> content = new ArrayList<>();
        for (DevProfileEntity entity : devProfileEntities.getContent()) {
            content.add(mapper.toDomain(entity));
        }
        return new PaginatedResult<>(
                content,
                page,
                size,
                devProfileEntities.getTotalElements()
        );
    }

}
