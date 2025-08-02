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
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DevProfileRepositoryImpl implements IDevProfileRepository {

    private final DevProfileMapper mapper;

    private final SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository;

    private final EntityManager manager;
    private final PasswordEncoder encoder;

    public DevProfileRepositoryImpl(SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository, DevProfileMapper mapper, EntityManager manager, PasswordEncoder encoder) {
        this.springDataPostgresDevProfileRepository = springDataPostgresDevProfileRepository;
        this.mapper = mapper;
        this.manager = manager;
        this.encoder = encoder;
    }


    @Override
    public DevProfile findById(Long id) throws EntityNotFoundException, BusinessException {
        return mapper.toDomain(springDataPostgresDevProfileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("DevProfile not found with id: " + id)));
    }

    @Override
    public DevProfile save(DevProfile profile) throws BusinessException {
        final DevProfileEntity entity = mapper.toEntity(profile);
        entity.setPassword(encoder.encode(profile.getPassword().getValue()));
        return mapper.toDomain(springDataPostgresDevProfileRepository.save(entity));
    }



    @Override
    public DevProfile update(DevProfile profile) throws BusinessException {
        final DevProfileEntity entity = mapper.toEntity(profile);
        return mapper.toDomain(springDataPostgresDevProfileRepository.save(entity));
    }

    @Override
    public void deleteProfile(Long id) throws EntityNotFoundException {
        final DevProfileEntity toDelete = springDataPostgresDevProfileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("DevProfile not found with id: " + id));
        toDelete.setIsActive(false);
        springDataPostgresDevProfileRepository.save(toDelete);
    }


    @Override
    public PaginatedResult<DevProfile> findAll(int page, int size) throws BusinessException {
        final Page<DevProfileEntity> devProfileEntities = springDataPostgresDevProfileRepository.findAll(Pageable.ofSize(size).withPage(page));
        final List<DevProfile> content = new ArrayList<>();
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
        final CriteriaBuilder cb = manager.getCriteriaBuilder();
        final CriteriaQuery<DevProfileEntity> query = cb.createQuery(DevProfileEntity.class);
        final Root<DevProfileEntity> root = query.from(DevProfileEntity.class);

        final List<Predicate> predicates = buildPredicates(filter, cb, root);
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        final var typedQuery = manager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size);

        final List<DevProfileEntity> results = typedQuery.getResultList();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        final Root<DevProfileEntity> countRoot = countQuery.from(DevProfileEntity.class);
        final List<Predicate> countPredicates = buildPredicates(filter, cb, countRoot);
        countQuery.select(cb.count(countRoot)).where(cb.and(countPredicates.toArray(new Predicate[0])));
        final long totalElements = manager.createQuery(countQuery).getSingleResult();
        final List<DevProfile> content = new ArrayList<>();
        for (DevProfileEntity entity : results) {
            content.add(mapper.toDomain(entity));
        }
        return new PaginatedResult<>(content, page, size, totalElements);
    }

    @Override
    public DevProfile findByEmail(String email) throws BusinessException, EntityNotFoundException {
        return mapper.toDomain(springDataPostgresDevProfileRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Invalid Credentials")));
    }


    private List<Predicate> buildPredicates(DevProfileFilter filter, CriteriaBuilder cb, Root<DevProfileEntity> root) {
        final List<Predicate> predicates = new ArrayList<>();

        if (filter.name() != null && !filter.name().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
        }

        if (filter.city() != null && !filter.city().isBlank()) {
            predicates.add(cb.equal(cb.lower(root.get("address").get("city")), filter.city().toLowerCase()));
        }

        if (filter.stack() != null && !filter.stack().isEmpty()) {
            final Join<DevProfileEntity, String> techJoin = root.join("techStack");
            predicates.add(techJoin.in(filter.stack()));
        }

        return predicates;
    }
}
