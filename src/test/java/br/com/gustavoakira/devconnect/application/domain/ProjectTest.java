package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    @Test
    void shouldCreateValidProject() throws BusinessException {
        assertDoesNotThrow(()->new Project(1L,"project A","fsfssaf","https://github.com/kira",1L));
        assertDoesNotThrow( ()->new Project("project A","fsfssaf","https://github.com/kira",1L));
    }
    @Test
    void shouldThrownBusinessWhenDevProfileIdIsInvalid(){
        final BusinessException ex = assertThrows(BusinessException.class, () -> {
            new Project("Project A","","https://github.com/kira",0L);
        });
        assertTrue(ex.getMessage().contains("id"));
    }

    @Test
    void shouldThrownBusinessWhenNameIsInvalid(){
        final BusinessException ex = assertThrows(BusinessException.class, () -> {
            new Project("Pro","","https://github.com/kira",1L);
        });
        assertTrue(ex.getMessage().contains("name"));
    }
    @Test
    void shouldThrownBusinessWhenRepoUrlIsInvalid(){
        final BusinessException ex = assertThrows(BusinessException.class, () -> {
            new Project("Project A","","https://githb.com/kira",1L);
        });
        assertTrue(ex.getMessage().contains("github"));
    }
}