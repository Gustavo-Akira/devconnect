package br.com.gustavoakira.devconnect.adapters.outbound.publishers.passwordrecovery.requested;

import br.com.gustavoakira.devconnect.adapters.config.TopicProperties;
import br.com.gustavoakira.devconnect.adapters.outbound.publishers.passwordrecovery.requested.event.PasswordRecoveryRequestEvent;
import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.publishers.PasswordRecoveryRequestPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPasswordRecoveryRequestPublisher implements PasswordRecoveryRequestPublisher {


    private final Logger logger = LoggerFactory.getLogger(KafkaPasswordRecoveryRequestPublisher.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TopicProperties topicProperties;

    public KafkaPasswordRecoveryRequestPublisher(KafkaTemplate<String, Object> kafkaTemplate, TopicProperties topicProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicProperties = topicProperties;
    }

    @Override
    public void send(PasswordRecovery recovery, User user) {
        final PasswordRecoveryRequestEvent event = new PasswordRecoveryRequestEvent(recovery.getUserId(), "http://localhost:8090"+recovery.getToken(), user.getEmail(), recovery.getExpiresAt());
        this.kafkaTemplate.send(topicProperties.getTopicName("auth.password-recovery.requested"), event).whenComplete(((_, ex) -> {
            if (ex != null) {
                logger.error("Failed to publish event for {}: {}", event.email(), ex.getMessage());
            } else {
                logger.info("Event published for email {} ", event.email());
            }
        }));
    }
}
