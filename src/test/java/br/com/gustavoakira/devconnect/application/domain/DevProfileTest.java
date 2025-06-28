package br.com.gustavoakira.devconnect.application.domain;


import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DevProfileTest {

    private Address sampleAddress() throws BusinessException {
        return new Address("Rua A", "Cidade X", "Estado Y", "BR", "12345-678");
    }

    @Test
    void shouldCreateValidDevProfile() throws BusinessException {
        DevProfile profile = new DevProfile("João Silva", "joao@email.com", "Str0ng@Pwd", "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva");

        assertEquals("João Silva", profile.getName());
        assertEquals("joao@email.com", profile.getEmail());
        assertTrue(profile.getPassword() instanceof Password);
        assertEquals("https://github.com/joaosilva", profile.getGithubLink());
    }

    @Test
    void shouldThrowExceptionForInvalidName() {
        Exception ex = assertThrows(BusinessException.class, () -> {
            new DevProfile("João", "email@email.com", "Str0ng@Pwd", "Bio válida com mais de 30 caracteres.", sampleAddress(), "https://github.com/joao", "https://linkedin.com/in/joao");
        });
        assertTrue(ex.getMessage().toLowerCase().contains("name"));
    }

    @Test
    void shouldThrowExceptionForInvalidGitHubLink() {
        Exception ex = assertThrows(BusinessException.class, () -> {
            new DevProfile("João Silva", "email@email.com", "Str0ng@Pwd", "Bio válida com mais de 30 caracteres.", sampleAddress(), "http://invalid.com/joao", "https://linkedin.com/in/joao");
        });
        assertTrue(ex.getMessage().toLowerCase().contains("github"));
    }

    @Test
    void shouldThrowExceptionForInvalidLinkedinLink() {
        Exception ex = assertThrows(BusinessException.class, () -> {
            new DevProfile("João Silva", "email@email.com", "Str0ng@Pwd", "Bio válida com mais de 30 caracteres.", sampleAddress(), "https://github.com/joao", "linkedin.com/in/joao");
        });
        assertTrue(ex.getMessage().toLowerCase().contains("linkedin"));
    }

    @Test
    void shouldChangePasswordSuccessfully() throws BusinessException {
        DevProfile profile = new DevProfile("João Silva", "joao@email.com", "Str0ng@Pwd", "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva");

        profile.changePassword("An0ther@Pass");
        assertEquals("An0ther@Pass", profile.getPassword().getValue());
    }

    @Test
    void shouldMoveToNewAddressSuccessfully() throws BusinessException {
        DevProfile profile = new DevProfile("João Silva", "joao@email.com", "Str0ng@Pwd", "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva");

        Address newAddress = new Address("Rua B", "Cidade Z", "Estado W", "CA", "98765-432");
        profile.moveToNewAddress(newAddress);
        assertEquals("Rua B", profile.getAddress().getStreet());
    }
}
