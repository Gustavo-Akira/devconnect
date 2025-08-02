package br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.AddressEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;
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
    void testToEntity_withValidDomain_shouldMapCorrectly() throws BusinessException {
        Address address = new Address("Rua A", "São Paulo", "SP", "BR", "12345-678");
        Password password = new Password("encoded-password");

        DevProfile domain = new DevProfile(
                1L,
                "Gustavo Akira",
                "gustavo@example.com",
                password.getValue(),
                "Java Dev",
                address,
                "https://github.com/gustavo",
                "https://linkedin.com/in/gustavo",
                Collections.singletonList("Java, Spring"),
                true
        );

        DevProfileEntity entity = mapper.toEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("Gustavo Akira");
        assertThat(entity.getEmail()).isEqualTo("gustavo@example.com");
        assertThat(entity.getPassword()).isEqualTo("encoded-password");
        assertThat(entity.getBio()).isEqualTo("Java Dev");
        assertThat(entity.getGithubLink()).isEqualTo("https://github.com/gustavo");
        assertThat(entity.getLinkedinLink()).isEqualTo("https://linkedin.com/in/gustavo");
        assertThat(entity.getTechStack()).isEqualTo(domain.getStack());
        assertThat(entity.getIsActive()).isTrue();
        assertThat(entity.getAddress()).isNotNull();
        assertThat(entity.getAddress().getCity()).isEqualTo("São Paulo");
    }

    @Test
    void testToDomain_withValidEntity_shouldMapCorrectly() throws BusinessException {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("Rua B");
        addressEntity.setCity("Rio de Janeiro");
        addressEntity.setState("RJ");
        addressEntity.setCountry("BR");
        addressEntity.setZipCode("87654-321");

        DevProfileEntity entity = new DevProfileEntity(
                2L,
                "Akira Uekita",
                "akira@example.com",
                "secure-hash",
                "Backend Dev",
                addressEntity,
                "https://github.com/akira",
                "https://linkedin.com/in/akira",
                Collections.singletonList("Node.js, TypeScript"),
                true
        );

        DevProfile domain = mapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(2L);
        assertThat(domain.getName()).isEqualTo("Akira Uekita");
        assertThat(domain.getEmail()).isEqualTo("akira@example.com");
        assertThat(domain.getPassword().getValue()).isEqualTo("secure-hash");
        assertThat(domain.getAddress()).isNotNull();
        assertThat(domain.getAddress().getCity()).isEqualTo("Rio de Janeiro");
    }

    @Test
    void testToEntity_withNull_shouldReturnNull() {
        assertThat(mapper.toEntity((DevProfile) null)).isNull();
        assertThat(mapper.toEntity((Address) null)).isNull();
    }

    @Test
    void testToDomain_withNull_shouldReturnNull() throws BusinessException {
        assertThat(mapper.toDomain((DevProfileEntity) null)).isNull();
        assertThat(mapper.toDomain((AddressEntity) null)).isNull();
    }
    @Test
    void toEntity_shouldReturnNull_whenDomainIsNull() {
        DevProfileEntity result = mapper.toEntity((DevProfile) null);
        assertThat(result).isNull();
    }

    @Test
    void toDomain_shouldReturnNull_whenEntityIsNull() throws Exception {
        DevProfile result = mapper.toDomain((DevProfileEntity) null);
        assertThat(result).isNull();
    }

    @Test
    void toEntity_shouldReturnNull_whenAddressIsNull() {
        AddressEntity result = mapper.toEntity((Address) null);
        assertThat(result).isNull();
    }

    @Test
    void toDomain_shouldReturnNull_whenAddressEntityIsNull() throws Exception {
        Address result = mapper.toDomain((AddressEntity) null);
        assertThat(result).isNull();
    }
}