package br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity;

import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    @Column(unique = true)
    private String email;
    private Boolean isActive;

    public UserEntity(){

    }

    public UserEntity(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword().getValue();
        this.isActive = user.isActive();
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
