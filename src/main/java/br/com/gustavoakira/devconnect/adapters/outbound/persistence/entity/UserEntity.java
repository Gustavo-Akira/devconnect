package br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity;

import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity extends UserSuperEntity {
    public User toDomain() throws BusinessException {
        System.out.println(this.getName());
        return new User(
                this.getId(),
                this.getName(),
                this.getPassword(),
                this.getEmail(),
                this.getIsActive()
        );
    }
}
