package br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.AddressEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DevProfileMapperTest {

    private DevProfileMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DevProfileMapper();
    }

    @Test
    void toEntity_shouldMapProfileFields_only() throws BusinessException {
        final Address address = new Address(
                "Rua A",
                "São Paulo",
                "SP",
                "BR",
                "12345-678"
        );

        final DevProfile domain = new DevProfile(
                1L,
                10L,
                "Gustavo Akira",
                "Java Dev",
                address,
                "https://github.com/gustavo",
                "https://linkedin.com/in/gustavo",
                Collections.singletonList("Java")
        );

        final DevProfileEntity entity = mapper.toEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getUserId()).isEqualTo(10L);
        assertThat(entity.getName()).isEqualTo("Gustavo Akira");
        assertThat(entity.getBio()).isEqualTo("Java Dev");
        assertThat(entity.getGithubLink()).isEqualTo("https://github.com/gustavo");
        assertThat(entity.getLinkedinLink()).isEqualTo("https://linkedin.com/in/gustavo");
        assertThat(entity.getTechStack()).isEqualTo(domain.getStack());

        assertThat(entity.getAddress()).isNotNull();
        assertThat(entity.getAddress().getCity()).isEqualTo("São Paulo");
    }

    @Test
    void toDomain_shouldReadLegacyFields_correctly() throws BusinessException {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("Rua B");
        addressEntity.setCity("Rio de Janeiro");
        addressEntity.setState("RJ");
        addressEntity.setCountry("BR");
        addressEntity.setZipCode("87654-321");

        final DevProfileEntity entity = new DevProfileEntity(
                2L,
                20L,
                "Akira Uekita",
                "Backend Dev",
                addressEntity,
                "https://github.com/akira",
                "https://linkedin.com/in/akira",
                Collections.singletonList("Node.js")
        );
        final DevProfile domain = mapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(2L);
        assertThat(domain.getUserId()).isEqualTo(20L);
        assertThat(domain.getName()).isEqualTo("Akira Uekita");
        assertThat(domain.getBio()).isEqualTo("Backend Dev");

        assertThat(domain.getAddress()).isNotNull();
        assertThat(domain.getAddress().getCity()).isEqualTo("Rio de Janeiro");
    }

    @Test
    void toEntity_shouldReturnNull_whenDomainIsNull() {
        assertThat(mapper.toEntity((DevProfile) null)).isNull();
    }

    @Test
    void toDomain_shouldReturnNull_whenEntityIsNull() throws BusinessException {
        assertThat(mapper.toDomain((DevProfileEntity) null)).isNull();
    }

    @Test
    void toEntity_shouldReturnNull_whenAddressIsNull() {
        assertThat(mapper.toEntity((Address) null)).isNull();
    }

    @Test
    void toDomain_shouldReturnNull_whenAddressEntityIsNull() throws BusinessException {
        assertThat(mapper.toDomain((AddressEntity) null)).isNull();
    }
}
