package br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity;

import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {
    @Test
    void shouldCreateValidUserWhenAllInfoAreReturnedAndCalledToDomain() throws BusinessException {
        final var entity = new UserEntity();
        entity.setId(1L);
        entity.setIsActive(true);
        entity.setPassword("afsfa");
        entity.setName("akira aasfaafa");
        entity.setEmail("akirauekita@gmail.com");
        final User user = entity.toDomain();
        assertEquals(entity.getEmail(), user.getEmail());
        assertEquals(entity.getIsActive(), user.isActive());
        assertEquals(entity.getPassword(), user.getPassword().getValue());
        assertEquals(entity.getId(), user.getId());
    }

    @Test
    void shouldThrowBusinessExceptionWhenDomainIsViolatedByCorruptedData(){
        final var entity = new UserEntity();
        entity.setEmail("");
        entity.setName("");
        assertThrows(BusinessException.class, entity::toDomain);
    }
}