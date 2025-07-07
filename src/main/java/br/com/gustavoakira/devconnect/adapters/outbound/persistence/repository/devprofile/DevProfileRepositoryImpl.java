package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers.DevProfileMapper;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.filters.DevProfileFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DevProfileRepositoryImpl implements IDevProfileRepository {

    private final DevProfileMapper mapper;

    private final SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository;

    private final EntityManager manager;

    public DevProfileRepositoryImpl(SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository, DevProfileMapper mapper, EntityManager manager) {
        this.springDataPostgresDevProfileRepository = springDataPostgresDevProfileRepository;
        this.mapper = mapper;
        this.manager = manager;
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

    @Override
    public PaginatedResult<DevProfile> findAllWithFilter(DevProfileFilter filter, int page, int size) throws BusinessException {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<DevProfile> query = cb.createQuery(DevProfile.class);
        Root<DevProfile> root = query.from(DevProfile.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.name() != null && !filter.name().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
        }

        if (filter.city() != null && !filter.city().isBlank()) {
            predicates.add(cb.equal(cb.lower(root.get("address").get("city")), filter.city().toLowerCase()));
        }

        if (filter.tech() != null && !filter.tech().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("techStack")), "%" + filter.tech().toLowerCase() + "%"));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        var typedQuery = manager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size);

        List<DevProfile> results = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<DevProfile> countRoot = countQuery.from(DevProfile.class);
        countQuery.select(cb.count(countRoot)).where(cb.and(predicates.toArray(new Predicate[0])));
        long totalElements = manager.createQuery(countQuery).getSingleResult();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PaginatedResult<>(results, page, totalPages, totalElements);
    }

}
