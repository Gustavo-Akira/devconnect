package br.com.gustavoakira.devconnect.application.services.devprofile;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.publishers.CreateDevProfileEventPublisher;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.SaveDevProfileCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveDevProfileUseCaseServiceTest {

    @Mock
    private IDevProfileRepository repository;

    @Mock
    private CreateDevProfileEventPublisher publisher;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private SaveDevProfileUseCaseService saveDevProfileUseCaseService;

    @Test
    void shouldSaveDevProfileWhenInformationIsValid() throws BusinessException {
        final SaveDevProfileCommand command = new SaveDevProfileCommand("Akira Uekita","akirauekita2002@gmail.com","Str@ngP4ssword","Avenida Joao","São Paulo","São Paulo","04724-003","BR","https://github.com/gustavo-Akira/","https://www.linkedin.com/in/gustavo-akira-uekita","gadsgdsdsgdggadsgadsgdsgddasgdasggdasgadsgadsgadgaddasgadsgdasdasgdasg",new ArrayList<>());
        Mockito.when(repository.save(Mockito.any())).thenReturn(new DevProfile(1L,"Akira Uekita","akirauekita2002@gmail.com","Str@ngP4ssword", "gadsgdsdsgdggadsgadsgdsgddasgdasggdasgadsgadsgadgaddasgadsgdasdasgdasg", new Address("Avenida Joao","São Paulo","São Paulo","BR","04724-003") ,"https://github.com/gustavo-Akira/","https://www.linkedin.com/in/gustavo-akira-uekita",new ArrayList<>(),true));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(new User(1L,"Akira Uekita","password", "akirauekita2002@gmail.com", true));
        Mockito.doNothing().when(publisher).sendMessage(Mockito.any());
        final DevProfile profile = saveDevProfileUseCaseService.execute(command);
        assertEquals("Akira Uekita",profile.getName());
    }

    @Test
    void shouldThrowBusinessExceptionWhenAnyInformationIsInvalid() throws BusinessException {
        final SaveDevProfileCommand command = new SaveDevProfileCommand("Uekita","akirauekita2002@gmail.com","Str@ngP4ssword","Avenida Joao","São Paulo","São Paulo","04724-003","BR","https://github.com/gustavo-Akira/","https://www.linkedin.com/in/gustavo-akira-uekita","gadsgdsdsgdggadsgadsgdsgddasgdasggdasgadsgadsgadgaddasgadsgdasdasgdasg",new ArrayList<>());
        final BusinessException exception = assertThrows(BusinessException.class,()->saveDevProfileUseCaseService.execute(command));
        assertEquals("The name cannot be with only one word",exception.getMessage());
    }
}