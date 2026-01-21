package br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity;

import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity extends UserSuperEntity {

    public UserEntity(){

    }

    public UserEntity(User user){
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword().getValue());
        this.setIsActive(user.isActive());
    }


    public User toDomain() throws BusinessException {
        return new User(
                this.getId(),
                this.getName(),
                this.getPassword(),
                this.getEmail(),
                this.getIsActive()
        );
    }
}
