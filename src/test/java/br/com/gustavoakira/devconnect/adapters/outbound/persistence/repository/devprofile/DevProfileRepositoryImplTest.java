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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DevProfileRepositoryImplTest {

    @Mock
    private SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository;

    @Mock
    private EntityManager manager;

    private final DevProfileMapper mapper = new DevProfileMapper();

    @InjectMocks
    private DevProfileRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new DevProfileRepositoryImpl(
                springDataPostgresDevProfileRepository,
                mapper,
                manager
        );
    }

    @Test
    void shouldSaveAndReturnDomainDevProfile() throws BusinessException {

        final DevProfile domainProfile = new DevProfile(
                1L,
                10L,
                "Akira Uekita",
                "akira@email.com",     // legacy (ignored on write)
                "hashed-pass",         // legacy (ignored on write)
                "Backend Dev",
                new Address(
                        "Avenida Joao Dias",
                        "São Paulo",
                        "SP",
                        "BR",
                        "04724-003"
                ),
                "https://github.com/akira",
                "https://linkedin.com/in/akira",
                new ArrayList<>(),
                true
        );

        final DevProfileEntity entity = getEntity();
        entity.setId(1L);
        entity.setPassword("Str@ngPassword");
        entity.setEmail("akirauekita2002@gmail.com");

        Mockito.when(springDataPostgresDevProfileRepository.save(Mockito.any()))
                .thenReturn(entity);

        final DevProfile result = repository.save(domainProfile);

        Mockito.verify(springDataPostgresDevProfileRepository)
                .save(Mockito.any(DevProfileEntity.class));

        assertNotNull(result);
        assertEquals(domainProfile.getName(), result.getName());
        assertEquals(domainProfile.getUserId(), result.getUserId());
    }

    @Nested
    class GetDevProfile {

        @Test
        void shouldReturnDevProfileWhenProfileExists() throws EntityNotFoundException, BusinessException {
            final Long id = 1L;
            final DevProfileEntity entity = getEntity();
            entity.setId(id);

            entity.setEmail("akira@email.com");
            entity.setPassword("secure-hash");
            entity.setIsActive(true);

            Mockito.when(springDataPostgresDevProfileRepository.findById(id))
                    .thenReturn(Optional.of(entity));

            final DevProfile devProfile = repository.findById(id);

            assertEquals("Akira Uekita", devProfile.getName());
            assertEquals("akira@email.com", devProfile.getEmail());
            assertEquals("secure-hash", devProfile.getPassword().getValue());
        }

        @Test
        void shouldThrowEntityNotFoundExceptionWhenDevProfileDoesNotExist() {
            Mockito.when(springDataPostgresDevProfileRepository.findById(99L))
                    .thenReturn(Optional.empty());

            assertThrows(
                    EntityNotFoundException.class,
                    () -> repository.findById(99L)
            );
        }
    }

    @Nested
    class FindDevProfileByEmail {

        @Test
        void shouldReturnDevProfileWhenProfileExists() throws EntityNotFoundException, BusinessException {
            final DevProfileEntity entity = getEntity();
            entity.setEmail("akira@email.com");
            entity.setPassword("hash");

            Mockito.when(springDataPostgresDevProfileRepository.findByEmail("akira@email.com"))
                    .thenReturn(Optional.of(entity));

            final DevProfile profile = repository.findByEmail("akira@email.com");

            assertEquals("Akira Uekita", profile.getName());
        }
    }

    private static DevProfileEntity getEntity() {
        return new DevProfileEntity(
                10L,
                "Akira Uekita",
                "Backend Dev",
                new AddressEntity(
                        "Avenida Joao Dias",
                        "São Paulo",
                        "SP",
                        "BR",
                        "04724-003"
                ),
                "https://github.com/akira",
                "https://linkedin.com/in/akira",
                new ArrayList<>()
        );
    }
}
