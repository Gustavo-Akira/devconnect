package br.com.gustavoakira.devconnect.adapters.outbound.persistence.mappers;

import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.AddressEntity;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.DevProfileEntity;
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

        AddressEntity addressEntity = toEntity(domain.getAddress());

        return new DevProfileEntity(
                domain.getId(),
                domain.getName(),
                domain.getEmail(),
                passwordToString(domain.getPassword()),
                domain.getBio(),
                addressEntity,
                domain.getGithubLink(),
                domain.getLinkedinLink(),
                domain.isActive()
        );
    }

    public DevProfile toDomain(DevProfileEntity entity) throws BusinessException {
        if (entity == null) {
            return null;
        }

        Address address = toDomain(entity.getAddress());

        Password password = stringToPassword(entity.getPassword());

        // Use construtor alternativo que não lança exceção
        // ou envolva em try/catch se usar o construtor que lança exceção
        DevProfile devProfile;
        try {
            devProfile = new DevProfile(
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    password.getValue(),
                    entity.getBio(),
                    address,
                    entity.getGithubLink(),
                    entity.getLinkedinLink(),
                    entity.getTechStack(),
                    entity.getIsActive()
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

    // Address -> AddressEntity
    public AddressEntity toEntity(Address address) {
        if (address == null) {
            return null;
        }
        AddressEntity entity = new AddressEntity();
        entity.setStreet(address.getStreet());
        entity.setCity(address.getCity());
        entity.setState(address.getState());
        entity.setCountry(address.getCountry());
        entity.setZipCode(address.getZipCode());
        return entity;
    }

    // Password -> String
    private String passwordToString(Password password) {
        if (password == null) {
            return null;
        }
        return password.getValue();
    }

    // String -> Password
    private Password stringToPassword(String passwordString) throws BusinessException {
        if (passwordString == null) {
            return null;
        }
        return new Password(passwordString);
    }
}
