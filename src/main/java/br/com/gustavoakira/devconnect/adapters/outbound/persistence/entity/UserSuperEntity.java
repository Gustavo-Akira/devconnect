package br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity;

import br.com.gustavoakira.devconnect.application.domain.value_object.Password;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSuperEntity {
    @Id
    private Long id;
    private String name;
    private String password;
    private String email;
    private Boolean isActive;
}
