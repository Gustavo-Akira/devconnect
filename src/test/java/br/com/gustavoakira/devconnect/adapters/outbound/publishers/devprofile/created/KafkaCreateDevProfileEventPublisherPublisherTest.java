package br.com.gustavoakira.devconnect.adapters.outbound.publishers.devprofile.created;

import br.com.gustavoakira.devconnect.adapters.config.TopicProperties;
import br.com.gustavoakira.devconnect.adapters.outbound.publishers.devprofile.created.event.DevProfileCreatedEvent;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KafkaCreateDevProfileEventPublisherPublisherTest {
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private TopicProperties topicProperties;

    @Mock
    private Logger logger;

    @InjectMocks
    private KafkaCreateDevProfileEventPublisherPublisher publisher;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        final var field = KafkaCreateDevProfileEventPublisherPublisher.class.getDeclaredField("logger");
        field.setAccessible(true);
        field.set(publisher, logger);
    }

    @Test
    void shouldSendEventSuccessfully() throws BusinessException {
        final DevProfile profile = getDevProfile();
        profile.setId(111L);
        Mockito.when(topicProperties.getTopicName("dev-profile.created")).thenReturn("dev-profile.created.v1");

        final CompletableFuture<SendResult<String,Object>> future = new CompletableFuture<>();
        future.complete(Mockito.mock(SendResult.class));
        Mockito.when(kafkaTemplate.send(Mockito.anyString(), Mockito.any())).thenReturn(future);

        publisher.sendMessage(profile);

        Mockito.verify(kafkaTemplate, Mockito.times(1))
                .send(Mockito.eq("dev-profile.created.v1"), Mockito.any(DevProfileCreatedEvent.class));

        Mockito.verify(logger, Mockito.timeout(1000).times(1))
                .info(Mockito.contains("Event published"), Mockito.any(Object.class));
    }



    @Test
    void shouldLogErrorWhenSendFails() throws BusinessException {
        final DevProfile profile = getDevProfile();
        profile.setId(999L);
        Mockito.when(topicProperties.getTopicName("dev-profile.created")).thenReturn("dev-profile.created.v1");

        final CompletableFuture<SendResult<String,Object>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka unavailable"));
        Mockito.when(kafkaTemplate.send(Mockito.anyString(), Mockito.any())).thenReturn(future);

        publisher.sendMessage(profile);

        Mockito.verify(logger, Mockito.timeout(1000).times(1))
                .error(Mockito.contains("Failed to publish event"), Mockito.eq(999L),Mockito.contains("Kafka unavailable"));
    }

    private static DevProfile getDevProfile() throws BusinessException {
        return new DevProfile(
                1L,
                "Akira Uekita",
                "akirauekita2002@gmail.com",
                "Str@ngP4ssword",
                "fasfsdfdsfdsafdfdfsdfsdfsdfdfsdsfdsfsdffd",
                new Address("Avenida Joao Dias", "2048", "São Paulo", "BR", "04724-003"),
                "https://github.com/Gustavo-Akira",
                "https://www.linkedin.com/in/gustavo-akira-uekita/",
                new ArrayList<>(),
                true
        );
    }
}