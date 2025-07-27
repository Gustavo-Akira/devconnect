package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.devprofile;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.AddressEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers.DevProfileMapper;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DevProfileRepositoryImplTest {
    @Mock
    private SpringDataPostgresDevProfileRepository springDataPostgresDevProfileRepository;
    private final DevProfileMapper mapper = new DevProfileMapper();
    private DevProfileRepositoryImpl repository;
    @Mock
    private EntityManager manager;

    @BeforeEach
    void setUp() {
        repository = new DevProfileRepositoryImpl(springDataPostgresDevProfileRepository, mapper, manager);
    }


    @Test
    void shouldSaveAndReturnDomainDevProfile() throws BusinessException {

        DevProfile domainProfile = new DevProfile("Akira Uekita","akirauekita2002@gmail.com","Str@ngP4ssword","fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",new Address("Avenida Joao Dias","2048","São Paulo","BR","04724-003"),"https://github.com/Gustavo-Akira","https://www.linkedin.com/in/gustavo-akira-uekita/",new ArrayList<>(),true);
        DevProfileEntity entity = getEntity();
        entity.setId(1L);
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


    private static DevProfileEntity getEntity() {
        return new DevProfileEntity("Akira Uekita", "akirauekita2002@gmail.com", "Str@ngP4ssword", "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd", new AddressEntity("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"), "https://github.com/Gustavo-Akira", "https://www.linkedin.com/in/gustavo-akira-uekita/", new ArrayList<>(),true);
    }
}