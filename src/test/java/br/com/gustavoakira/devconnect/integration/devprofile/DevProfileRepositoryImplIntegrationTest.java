package br.com.gustavoakira.devconnect.integration.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.devprofile.DevProfileRepositoryImpl;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.filters.DevProfileFilter;
import br.com.gustavoakira.devconnect.shared.BasePostgresTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class DevProfileRepositoryImplIntegrationTest extends BasePostgresTest {

    @Autowired
    private DevProfileRepositoryImpl repository;

    private DevProfile createProfile() throws BusinessException {
        return new DevProfile(
                1L,
                "Akira Uekita",
                "akira.valid@email.com",
                "Str@ngP4ssword",
                "Backend Dev",
                new Address(
                        "Avenida Joao Dias",
                        "São Paulo",
                        "SP",
                        "BR",
                        "04724-003"
                ),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>(),
                true
        );
    }

    @Test
    void shouldSaveDevProfile_onlyProfileData() throws BusinessException {
        final DevProfile profile = createProfile();

        final DevProfile saved = repository.save(profile);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(profile.getName(), saved.getName());
        assertEquals(profile.getBio(), saved.getBio());
        assertEquals(profile.getAddress().getStreet(), saved.getAddress().getStreet());
    }

    @Test
    void shouldUpdateDevProfileProfileData() throws BusinessException {
        final DevProfile profile = repository.save(createProfile());

        profile.rename("Nome Atualizado");

        final DevProfile updated = repository.update(profile);

        assertEquals(profile.getId(), updated.getId());
        assertEquals("Nome Atualizado", updated.getName());
    }

    @Test
    void shouldFindDevProfileById() throws BusinessException, EntityNotFoundException {
        final DevProfile saved = repository.save(createProfile());

        final DevProfile found = repository.findById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getName(), found.getName());
        assertEquals(saved.getAddress().getStreet(), found.getAddress().getStreet());
    }

    @Test
    void shouldReturnPaginatedProfiles() throws BusinessException {
        repository.save(createProfile());

        final PaginatedResult<DevProfile> page = repository.findAll(0, 5);

        assertFalse(page.getContent().isEmpty());
        assertEquals(3, page.getTotalElements());
        assertEquals(5, page.getSize());
    }

    @Test
    void shouldReturnPaginatedProfilesWithFilter() throws BusinessException {
        repository.save(createProfile());

        final DevProfileFilter filter = new DevProfileFilter(null, "São Paulo", null);

        final PaginatedResult<DevProfile> page =
                repository.findAllWithFilter(filter, 0, 5);

        assertEquals(2, page.getTotalElements());
        assertEquals("Nome Atualizado", page.getContent().getFirst().getName());
    }

    @Test
    void shouldSoftDeleteProfile_preservingLegacyFields() throws BusinessException, EntityNotFoundException {
        final DevProfile saved = repository.save(createProfile());

        repository.deleteProfile(saved.getId());

        final DevProfile deleted = repository.findById(saved.getId());

        assertFalse(deleted.isActive());
    }
}
