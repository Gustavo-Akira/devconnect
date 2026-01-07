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
public class DevProfileRepositoryImplIntegrationTest extends BasePostgresTest {

    @Autowired
    private DevProfileRepositoryImpl repository;


    @Test
    void shouldSaveAndReturnDomainDevProfile() throws BusinessException {
        final DevProfile profile = new DevProfile(
                1L,
                "Akira Uekita",
                "akirauekita2002@gmail.com",
                "Str@ngP4ssword",
                "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",
                new Address("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>(),
                true
        );

        final DevProfile saved = repository.save(profile);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(profile.getEmail(), saved.getEmail());
        assertEquals(profile.getName(), saved.getName());
        assertTrue(saved.isActive());
        assertEquals("Avenida Joao Dias", saved.getAddress().getStreet());
    }

    @Test
    void shouldUpdateAndReturnDomainDevProfile() throws BusinessException {
        final DevProfile profile = new DevProfile(
                1L,
                "Akira Uekita",
                "akirauekita2003@gmail.com",
                "Str@ngP4ssword",
                "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",
                new Address("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>(),
                true
        );

        final DevProfile saved = repository.save(profile);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(profile.getEmail(), saved.getEmail());
        assertEquals(profile.getName(), saved.getName());
        assertTrue(saved.isActive());
        assertEquals("Avenida Joao Dias", saved.getAddress().getStreet());

        profile.setId(saved.getId());
        profile.rename("Mudei o Nome");
        final DevProfile updated = repository.update(profile);

        assertNotNull(updated);
        assertEquals(saved.getId(), updated.getId());
        assertNotEquals(saved.getName(), updated.getName());
        assertTrue(saved.isActive());
        assertEquals("Avenida Joao Dias", saved.getAddress().getStreet());

    }

    @Test
    void shouldGetDevProfileWhenExists() throws BusinessException, EntityNotFoundException {

        final DevProfile profile = new DevProfile(
                1L,
                "Akira Uekita",
                "akirauekita2004@gmail.com",
                "Str@ngP4ssword",
                "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",
                new Address("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>(),
                true
        );

        final DevProfile saved = repository.save(profile);

        final DevProfile returnedProfile = repository.findById(saved.getId());
        assertNotNull(returnedProfile);
        assertEquals(returnedProfile.getName(),saved.getName());
        assertEquals(returnedProfile.getAddress().getStreet(),saved.getAddress().getStreet());
    }

    @Test
    void shouldGetDevProfilePaginatedResultWhenExists() throws BusinessException, EntityNotFoundException {

        final DevProfile profile = new DevProfile(
                1L,
                "Akira Uekita",
                "akirauekita2005@gmail.com",
                "Str@ngP4ssword",
                "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",
                new Address("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>(),
                true
        );

        final DevProfile saved = repository.save(profile);

        final PaginatedResult<DevProfile> paginatedResult = repository.findAll(0,5);
        assertEquals(6,paginatedResult.getTotalElements());
        assertEquals(2, paginatedResult.getTotalPages());
        assertEquals(5,paginatedResult.getSize());

        final DevProfile returnedProfile = paginatedResult.getContent().getFirst();

        assertNotNull(returnedProfile);
        assertEquals(returnedProfile.getName(),saved.getName());
        assertEquals(returnedProfile.getAddress().getStreet(),saved.getAddress().getStreet());
    }

    @Test
    void shouldGetDevProfilePaginatedResultWithFilterWhenExists() throws BusinessException, EntityNotFoundException {

        final DevProfile profile = new DevProfile(
                1L,
                "Akira Uekita",
                "akirauekita2006@gmail.com",
                "Str@ngP4ssword",
                "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",
                new Address("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>(),
                true
        );

        final DevProfile saved = repository.save(profile);
        final DevProfileFilter filter = new DevProfileFilter(null, null, null);

        final PaginatedResult<DevProfile> paginatedResult = repository.findAllWithFilter(filter,0,5);
        assertEquals(1,paginatedResult.getTotalElements());
        assertEquals(1, paginatedResult.getTotalPages());
        assertEquals(5,paginatedResult.getSize());

        final DevProfile returnedProfile = paginatedResult.getContent().getFirst();

        assertNotNull(returnedProfile);
        assertEquals(returnedProfile.getName(),saved.getName());
        assertEquals(returnedProfile.getAddress().getStreet(),saved.getAddress().getStreet());
    }

    @Test
    void shouldSoftDeleteAndReturnDomainDevProfile() throws BusinessException, EntityNotFoundException {
        final DevProfile profile = new DevProfile(
                1L,
                "Akira Uekita",
                "akirauekita2007@gmail.com",
                "Str@ngP4ssword",
                "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",
                new Address("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>(),
                true
        );

        DevProfile saved = repository.save(profile);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(profile.getEmail(), saved.getEmail());
        assertEquals(profile.getName(), saved.getName());
        assertTrue(saved.isActive());
        assertEquals("Avenida Joao Dias", saved.getAddress().getStreet());

        repository.deleteProfile(saved.getId());
        saved = repository.findById(saved.getId());
        assertFalse(saved.isActive());
    }

    @Test
    void shouldFindByEmailAndReturnDomainDevProfile() throws BusinessException, EntityNotFoundException {
        final DevProfile profile = new DevProfile(
                1L,
                "Akira Uekita",
                "akirauekita2008@gmail.com",
                "Str@ngP4ssword",
                "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",
                new Address("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>(),
                true
        );

        final DevProfile saved = repository.save(profile);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(profile.getEmail(), saved.getEmail());
        assertEquals(profile.getName(), saved.getName());
        assertTrue(saved.isActive());
        assertEquals("Avenida Joao Dias", saved.getAddress().getStreet());

        final DevProfile findEmailProfile =  repository.findByEmail(saved.getEmail());
        assertEquals(saved.getId(),findEmailProfile.getId());
    }
}
