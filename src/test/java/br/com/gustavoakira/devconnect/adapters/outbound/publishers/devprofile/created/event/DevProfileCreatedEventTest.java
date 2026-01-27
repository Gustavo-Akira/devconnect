package br.com.gustavoakira.devconnect.adapters.outbound.publishers.devprofile.created.event;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DevProfileCreatedEventTest {
    @Test
    void shouldSetAllPropertiesEqualFromTheDevProfilePassedOnConstruct() throws BusinessException {
        final DevProfile devProfile = new DevProfile(
                1L,
                "Akira Uekita",
                "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",
                new Address("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>()
        );
        final DevProfileCreatedEvent event = new DevProfileCreatedEvent(devProfile);
        assertEquals(devProfile.getId(), event.getId());
        assertEquals(devProfile.getAddress().getCity(), event.getCity());
        assertEquals(devProfile.getStack(), event.getStack());
        assertEquals(devProfile.getName(), event.getName());
        assertEquals(devProfile.getAddress().getState(), event.getState());
        assertEquals(devProfile.getAddress().getCountry(), event.getCountry());
    }
}