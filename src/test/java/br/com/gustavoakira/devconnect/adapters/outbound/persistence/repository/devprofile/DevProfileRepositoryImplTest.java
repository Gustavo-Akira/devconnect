package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.AddressEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers.DevProfileMapper;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.filters.DevProfileFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DevProfileRepositoryImplTest {
    @Mock
    private SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository;
    private final DevProfileMapper mapper = new DevProfileMapper();
    @InjectMocks
    private DevProfileRepositoryImpl repository;
    @Mock
    private EntityManager manager;
    @Mock
    private PasswordEncoder encoder;

    @Mock
    private CriteriaBuilder cb;

    @Mock
    private CriteriaQuery<DevProfileEntity> query;

    @Mock
    private CriteriaQuery<Long> countQuery;

    @Mock
    private Root<DevProfileEntity> root;

    @Mock
    private Root<DevProfileEntity> countRoot;

    @Mock
    private TypedQuery<DevProfileEntity> typedQuery;

    @Mock
    private TypedQuery<Long> countTypedQuery;

    @BeforeEach
    void setUp() {
        repository = new DevProfileRepositoryImpl(springDataPostgresDevProfileRepository, mapper, manager, encoder);
    }


    @Test
    void shouldSaveAndReturnDomainDevProfile() throws BusinessException {

        DevProfile domainProfile = new DevProfile("Akira Uekita","akirauekita2002@gmail.com","Str@ngP4ssword","fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",new Address("Avenida Joao Dias","2048","São Paulo","BR","04724-003"),"https://github.com/Gustavo-Akira","https://www.linkedin.com/in/gustavo-akira-uekita/",new ArrayList<>(),true);
        DevProfileEntity entity = getEntity();
        entity.setId(1L);
        Mockito.when(encoder.encode(domainProfile.getPassword().getValue())).thenReturn(domainProfile.getPassword().getValue());
        Mockito.when(springDataPostgresDevProfileRepository.save(entity)).thenReturn(entity);
        domainProfile.setId(1L);

        DevProfile result = repository.save(domainProfile);

        Mockito.verify(springDataPostgresDevProfileRepository).save(entity);

        assertNotNull(result);
        assertEquals(domainProfile.getName(), result.getName());
    }

    @Nested
    class GetPaginatedDevProfiles{
        @Test
        void shouldReturnEmptyListWhenDevProfilesDoNotExists() throws BusinessException {
            Mockito.when(springDataPostgresDevProfileRepository.findAll(Pageable.ofSize(5).withPage(0))).thenReturn(Page.empty());
            PaginatedResult<DevProfile> devProfilesPage = repository.findAll(0,5);
            assertTrue(devProfilesPage.getContent().isEmpty());
            assertEquals(0,devProfilesPage.getTotalElements());
            assertEquals(0,devProfilesPage.getPage());
            assertEquals(5,devProfilesPage.getSize());
            assertEquals(0,devProfilesPage.getTotalPages());
        }

        @Test
        void shouldReturnPaginatedResultWhenDevProfilesExists() throws BusinessException {
            List<DevProfileEntity> entities = new ArrayList<>();
            for(int i=0;i<15;i++){
                entities.add(getEntity());
            }
            int totalElements = 15;
            int page = 0;
            int size = 5;

            Page<DevProfileEntity> pageResult = new PageImpl<>(entities, PageRequest.of(page, size), totalElements);

            Mockito.when(springDataPostgresDevProfileRepository.findAll(Mockito.eq(PageRequest.of(0, 5))))
                    .thenReturn(pageResult);


            PaginatedResult<DevProfile> devProfilesPage = repository.findAll(page, size);

            assertFalse(devProfilesPage.getContent().isEmpty());
            assertEquals(totalElements, devProfilesPage.getTotalElements());
            assertEquals(page, devProfilesPage.getPage());
            assertEquals(size, devProfilesPage.getSize());
            assertEquals(3, devProfilesPage.getTotalPages());
            assertEquals(15, devProfilesPage.getContent().size());
        }
    }

    @Nested
    class DeleteDevProfile{

        @Test
        void shouldSoftDeleteDevProfileWhenProfileExists() throws EntityNotFoundException {
            Long id = 1L;
            DevProfileEntity entity = getEntity();
            entity.setId(id);
            entity.setIsActive(true);

            Mockito.when(springDataPostgresDevProfileRepository.findById(id))
                    .thenReturn(Optional.of(entity));

            repository.deleteProfile(id);

            assertFalse(entity.getIsActive(), "isActive should be false after soft delete");
            Mockito.verify(springDataPostgresDevProfileRepository).save(entity);
        }

        @Test
        void shouldThrowEntityNotFoundExceptionWhenDevProfileDoesNotExist() {
            Long id = 99L;

            Mockito.when(springDataPostgresDevProfileRepository.findById(id))
                    .thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> repository.deleteProfile(id));
            assertEquals("DevProfile not found with id: " + id, exception.getMessage());

            Mockito.verify(springDataPostgresDevProfileRepository, Mockito.never()).save(Mockito.any());
        }
    }

    @Nested
    class GetDevProfile{

        @Test
        void shouldReturnDevProfileWhenProfileExists() throws EntityNotFoundException, BusinessException {
            Long id = 1L;
            DevProfileEntity entity = getEntity();
            entity.setId(id);
            entity.setIsActive(true);
            DevProfile domainProfile = new DevProfile(1L,"Akira Uekita","akirauekita2002@gmail.com","Str@ngP4ssword","fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",new Address("Avenida Joao Dias","2048","São Paulo","BR","04724-003"),"https://github.com/Gustavo-Akira","https://www.linkedin.com/in/gustavo-akira-uekita/",new ArrayList<>(),true);


            Mockito.when(springDataPostgresDevProfileRepository.findById(id))
                    .thenReturn(Optional.of(entity));
            DevProfile devProfile = repository.findById(id);
            assertEquals(domainProfile.getName(),devProfile.getName());
        }

        @Test
        void shouldThrowEntityNotFoundExceptionWhenDevProfileDoesNotExist() {
            Long id = 99L;

            Mockito.when(springDataPostgresDevProfileRepository.findById(id))
                    .thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> repository.findById(id));
            assertEquals("DevProfile not found with id: " + id, exception.getMessage());

            Mockito.verify(springDataPostgresDevProfileRepository).findById(Mockito.any());
        }
    }

    @Nested
    class FindDevProfileByEmail{
        @Test
        void shouldReturnDevProfileWhenProfileExists() throws EntityNotFoundException, BusinessException {
            Long id = 1L;
            DevProfileEntity entity = getEntity();
            String email = "akirauekita2002@gmail.com";
            entity.setId(id);
            entity.setIsActive(true);
            DevProfile domainProfile = new DevProfile(1L,"Akira Uekita",email,"Str@ngP4ssword","fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",new Address("Avenida Joao Dias","2048","São Paulo","BR","04724-003"),"https://github.com/Gustavo-Akira","https://www.linkedin.com/in/gustavo-akira-uekita/",new ArrayList<>(),true);


            Mockito.when(springDataPostgresDevProfileRepository.findByEmail(email))
                    .thenReturn(Optional.of(entity));
            DevProfile devProfile = repository.findByEmail(email);
            assertEquals(domainProfile.getName(),devProfile.getName());
        }

        @Test
        void shouldThrowEntityNotFoundExceptionWhenDevProfileDoesNotExist() {
            String email = "akirauekita22@gmail.com";

            Mockito.when(springDataPostgresDevProfileRepository.findByEmail(email))
                    .thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> repository.findByEmail(email));
            assertEquals("Invalid Credentials", exception.getMessage());

            Mockito.verify(springDataPostgresDevProfileRepository).findByEmail(Mockito.any());
        }
    }

    @Nested
    class FindAllWithFilterTest {

        @Test
        void shouldReturnPaginatedResultWithFilter() throws BusinessException {
            DevProfileFilter filter = new DevProfileFilter("gustavo", "são paulo", List.of("java"));
            DevProfileEntity entity = getEntity();

            Mockito.when(manager.getCriteriaBuilder()).thenReturn(cb);
            Mockito.when(cb.createQuery(DevProfileEntity.class)).thenReturn(query);
            Mockito.when(cb.createQuery(Long.class)).thenReturn(countQuery);
            Mockito.when(query.from(DevProfileEntity.class)).thenReturn(root);
            Mockito.when(countQuery.from(DevProfileEntity.class)).thenReturn(countRoot);
            Mockito.when(manager.createQuery(query)).thenReturn(typedQuery);
            Mockito.when(manager.createQuery(countQuery)).thenReturn(countTypedQuery);
            Mockito.when(typedQuery.setFirstResult(Mockito.anyInt())).thenReturn(typedQuery);
            Mockito.when(typedQuery.setMaxResults(Mockito.anyInt())).thenReturn(typedQuery);
            Mockito.when(typedQuery.getResultList()).thenReturn(List.of(entity));
            Mockito.when(countTypedQuery.getSingleResult()).thenReturn(1L);

            Predicate namePredicate = Mockito.mock(Predicate.class);
            Predicate cityPredicate = Mockito.mock(Predicate.class);
            Predicate stackPredicate = Mockito.mock(Predicate.class);
            Predicate combinedPredicate = Mockito.mock(Predicate.class);
            Expression countExpr = Mockito.mock(Expression.class);

            Path namePath = Mockito.mock(Path.class);
            Expression nameLower = Mockito.mock(Expression.class);
            Mockito.when(root.get("name")).thenReturn(namePath);
            Mockito.when(cb.lower(namePath)).thenReturn(nameLower);
            Mockito.when(cb.like(nameLower, "%gustavo%")).thenReturn(namePredicate);

            Path addressPath = Mockito.mock(Path.class);
            Path cityPath = Mockito.mock(Path.class);
            Expression cityLower = Mockito.mock(Expression.class);
            Mockito.when(root.get("address")).thenReturn(addressPath);
            Mockito.when(addressPath.get("city")).thenReturn(cityPath);
            Mockito.when(cb.lower(cityPath)).thenReturn(cityLower);
            Mockito.when(cb.equal(cityLower, "são paulo")).thenReturn(cityPredicate);

            Join techJoin = Mockito.mock(Join.class);
            Mockito.when(root.join("techStack")).thenReturn(techJoin);
            Mockito.when(techJoin.in(filter.stack())).thenReturn(stackPredicate);

            Path countNamePath = Mockito.mock(Path.class);
            Expression countNameLower = Mockito.mock(Expression.class);
            Mockito.when(countRoot.get("name")).thenReturn(countNamePath);
            Mockito.when(cb.lower(countNamePath)).thenReturn(countNameLower);
            Mockito.when(cb.like(countNameLower, "%gustavo%")).thenReturn(namePredicate);

            Path countAddressPath = Mockito.mock(Path.class);
            Path countCityPath = Mockito.mock(Path.class);
            Expression countCityLower = Mockito.mock(Expression.class);
            Mockito.when(countRoot.get("address")).thenReturn(countAddressPath);
            Mockito.when(countAddressPath.get("city")).thenReturn(countCityPath);
            Mockito.when(cb.lower(countCityPath)).thenReturn(countCityLower);
            Mockito.when(cb.equal(countCityLower, "são paulo")).thenReturn(cityPredicate);

            Join countTechJoin = Mockito.mock(Join.class);
            Mockito.when(countRoot.join("techStack")).thenReturn(countTechJoin);
            Mockito.when(countTechJoin.in(filter.stack())).thenReturn(stackPredicate);

            Mockito.when(cb.and(Mockito.any(Predicate[].class))).thenReturn(combinedPredicate);
            Mockito.when(cb.count(countRoot)).thenReturn(countExpr);

            Mockito.when(countQuery.select(Mockito.any())).thenReturn(countQuery);
            PaginatedResult<DevProfile> result = repository.findAllWithFilter(filter, 0, 10);

            assertNotNull(result);
            assertEquals(1, result.getContent().size());
            assertEquals(0, result.getPage());
            assertEquals(10, result.getSize());
            assertEquals(1L, result.getTotalElements());
        }
    }


    private static DevProfileEntity getEntity() {
        return new DevProfileEntity("Akira Uekita", "akirauekita2002@gmail.com", "Str@ngP4ssword", "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd", new AddressEntity("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"), "https://github.com/Gustavo-Akira", "https://www.linkedin.com/in/gustavo-akira-uekita/", new ArrayList<>(),true);
    }
}