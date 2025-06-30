package br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity;

import br.com.gustavoakira.devconnect.application.domain.value_object.Password;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class UserSuperEntity {
    @Id
    private Long id;
    private String name;
    private Password password;
    private String email;
    private Boolean isActive;
}
