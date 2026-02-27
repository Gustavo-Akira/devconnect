package br.com.gustavoakira.devconnect.adapters.outbound.publishers.passwordrecovery.requested;

import br.com.gustavoakira.devconnect.adapters.config.TopicProperties;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class KafkaPasswordRecoveryRequestPublisherTest {

    private KafkaTemplate<String, Object> kafkaTemplate;
    private TopicProperties topicProperties;
    private KafkaPasswordRecoveryRequestPublisher publisher;

    @BeforeEach
    void setup() {
        kafkaTemplate = mock(KafkaTemplate.class);
        topicProperties = mock(TopicProperties.class);
        publisher = new KafkaPasswordRecoveryRequestPublisher(kafkaTemplate, topicProperties);
    }

    @Test
    void shouldPublishPasswordRecoveryEventSuccessfully() {
        final PasswordRecovery recovery = mock(PasswordRecovery.class);
        final User user = mock(User.class);

        when(recovery.getUserId()).thenReturn(12L);
        when(recovery.getToken()).thenReturn("/token-123");
        when(recovery.getExpiresAt()).thenReturn(Instant.now());
        when(user.getEmail()).thenReturn("user@email.com");
        when(topicProperties.getTopicName("auth.password-recovery.requested"))
                .thenReturn("recovery-topic");

        final CompletableFuture<SendResult<String, Object>> future =
                CompletableFuture.completedFuture(mock(SendResult.class));

        when(kafkaTemplate.send(eq("recovery-topic"), any()))
                .thenReturn(future);

        publisher.send(recovery, user);

        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);

        verify(kafkaTemplate).send(eq("recovery-topic"), eventCaptor.capture());

        Object capturedEvent = eventCaptor.getValue();
        assertThat(capturedEvent).isNotNull();
    }

    @Test
    void shouldHandleErrorWhenPublishingFails() {
        final PasswordRecovery recovery = mock(PasswordRecovery.class);
        final User user = mock(User.class);

        when(recovery.getUserId()).thenReturn(12L);
        when(recovery.getToken()).thenReturn("/token-123");
        when(recovery.getExpiresAt()).thenReturn(Instant.now());
        when(user.getEmail()).thenReturn("user@email.com");
        when(topicProperties.getTopicName("auth.password-recovery.requested"))
                .thenReturn("recovery-topic");

        final CompletableFuture<SendResult<String, Object>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka error"));

        when(kafkaTemplate.send(eq("recovery-topic"), any()))
                .thenReturn(future);

        publisher.send(recovery, user);

        verify(kafkaTemplate).send(eq("recovery-topic"), any());
    }
}