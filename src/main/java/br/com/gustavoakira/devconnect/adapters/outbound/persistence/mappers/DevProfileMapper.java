package br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.AddressEntity;
import br.com.  gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;
import org.springframework.stereotype.Component;

@Component
public class DevProfileMapper {
    public DevProfileEntity toEntity(DevProfile domain) {
        if (domain == null) {
            return null;
        }

        final AddressEntity addressEntity = toEntity(domain.getAddress());

        return new DevProfileEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getName(),
                domain.getBio(),
                addressEntity,
                domain.getGithubLink(),
                domain.getLinkedinLink(),
                domain.getStack()
        );
    }

    public DevProfile toDomain(DevProfileEntity entity) throws BusinessException {
        if (entity == null) {
            return null;
        }

        final Address address = toDomain(entity.getAddress());


        final DevProfile devProfile;
        try {
            devProfile = new DevProfile(
                    entity.getId(),
                    entity.getUserId(),
                    entity.getName(),
                    entity.getBio(),
                    address,
                    entity.getGithubLink(),
                    entity.getLinkedinLink(),
                    entity.getTechStack()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar DevProfile do domínio: " + e.getMessage(), e);
        }

        return devProfile;
    }

    // AddressEntity -> Address
    public Address toDomain(AddressEntity entity) throws BusinessException {
        if (entity == null) {
            return null;
        }
        return new Address(
                entity.getStreet(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getZipCode()
        );
    }

    public AddressEntity toEntity(Address address) {
        if (address == null) {
            return null;
        }
        final AddressEntity entity = new AddressEntity();
        entity.setStreet(address.getStreet());
        entity.setCity(address.getCity());
        entity.setState(address.getState());
        entity.setCountry(address.getCountry());
        entity.setZipCode(address.getZipCode());
        return entity;
    }

    private String passwordToString(Password password) {
        if (password == null) {
            return null;
        }
        return password.getValue();
    }

    private Password stringToPassword(String passwordString) {
        if (passwordString == null) {
            return null;
        }
        return new Password(passwordString);
    }
}
