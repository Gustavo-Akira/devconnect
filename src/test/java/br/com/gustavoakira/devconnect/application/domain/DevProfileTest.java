package br.com.gustavoakira.devconnect.application.domain;


import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DevProfileTest {

    private Address sampleAddress() throws BusinessException {
        return new Address("Rua A", "Cidade X", "Estado Y", "BR", "12345-678");
    }

    @Test
    void shouldCreateValidDevProfile() throws BusinessException {
        final DevProfile profile = new DevProfile(1L,"João Silva", "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>());

        assertEquals("João Silva", profile.getName());
        assertEquals("https://github.com/joaosilva", profile.getGithubLink());
    }

    @Test
    void shouldThrowExceptionForInvalidName() {
        final Exception ex = assertThrows(BusinessException.class, () -> {
            new DevProfile(1L,"João", "Bio válida com mais de 30 caracteres.", sampleAddress(), "https://github.com/joao", "https://linkedin.com/in/joao",new ArrayList<>());
        });
        assertTrue(ex.getMessage().toLowerCase().contains("name"));
    }

    @Test
    void shouldThrowExceptionForInvalidGitHubLink() {
        final Exception ex = assertThrows(BusinessException.class, () -> {
            new DevProfile(1L,"João Silva", "Bio válida com mais de 30 caracteres.", sampleAddress(), "http://invalid.com/joao", "https://linkedin.com/in/joao",new ArrayList<>());
        });
        assertTrue(ex.getMessage().toLowerCase().contains("github"));
    }

    @Test
    void shouldThrowExceptionForInvalidLinkedinLink() {
        final Exception ex = assertThrows(BusinessException.class, () -> {
            new DevProfile(1L,"João Silva", "Bio válida com mais de 30 caracteres.", sampleAddress(), "https://github.com/joao", "linkedin.com/in/joao",new ArrayList<>());
        });
        assertTrue(ex.getMessage().toLowerCase().contains("linkedin"));
    }


    @Test
    void shouldMoveToNewAddressSuccessfully() throws BusinessException {
        final DevProfile profile = new DevProfile(1L,"João Silva", "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>());

        final Address newAddress = new Address("Rua B", "Cidade Z", "Estado W", "CA", "98765-432");
        profile.moveToNewAddress(newAddress);
        assertEquals("Rua B", profile.getAddress().getStreet());
    }

    @Test
    void shouldSetNewBioWhenUpdateBioIsCalled() throws BusinessException {
        final DevProfile profile = new DevProfile(1L,"João Silva", "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>());
        profile.updateBio("new Bio");
        assertEquals("new Bio", profile.getBio());
    }

    @Test
    void shouldSetNewGithubLinkWhenUpdateGithubLinkIsCalled() throws BusinessException {
        final DevProfile profile = new DevProfile(1L,"João Silva", "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>());
        profile.updateGithubLink("https://github.com/novo");
        assertEquals("https://github.com/novo", profile.getGithubLink());
    }

    @Test
    void shouldSetNewLinkedinLinkWhenUpdateLinkedinLinkIsCalled() throws BusinessException {
        final DevProfile profile = new DevProfile(1L,"João Silva","Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>());
        profile.updateLinkedinLink("https://linkedin.com/in/novo");
        assertEquals("https://linkedin.com/in/novo", profile.getLinkedinLink());
    }

    @Test
    void shouldThrowBusinessExceptionWhenUpdateLinkedinLinkIsCalledWithInvalidLink() throws BusinessException {
        final DevProfile profile = new DevProfile(1L,"João Silva", "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>());
        assertThrows(BusinessException.class,()->profile.updateLinkedinLink("https://com/in/novo"));
    }

    @Test
    void shouldThrowBusinessExceptionWhenUpdateGithubLinkIsCalledWithInvalidLink() throws BusinessException {
        final DevProfile profile = new DevProfile(1L,"João Silva",  "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>());
        assertThrows(BusinessException.class,()->profile.updateGithubLink("https://com/in/novo"));
    }

    @Test
    void shouldSetNewStackWhenUpdateStackCalled() throws BusinessException {
        final DevProfile profile = new DevProfile(1L,"João Silva", "Desenvolvedor backend com 10 anos de experiência.", sampleAddress(), "https://github.com/joaosilva", "https://linkedin.com/in/joaosilva",new ArrayList<>());
        profile.updateStack(new ArrayList<>());
        assertEquals(new ArrayList<>(), profile.getStack());
    }
}
