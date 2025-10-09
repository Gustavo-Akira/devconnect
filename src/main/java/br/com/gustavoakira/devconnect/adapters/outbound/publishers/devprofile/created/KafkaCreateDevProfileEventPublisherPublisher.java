package br.com.gustavoakira.devconnect.adapters.outbound.publishers.devprofile.created;

import br.com.gustavoakira.devconnect.adapters.config.TopicProperties;
import br.com.gustavoakira.devconnect.adapters.outbound.publishers.devprofile.created.event.DevProfileCreatedEvent;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.publishers.CreateDevProfileEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaCreateDevProfileEventPublisherPublisher implements CreateDevProfileEventPublisher {
    private final Logger logger = LoggerFactory.getLogger(KafkaCreateDevProfileEventPublisherPublisher.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TopicProperties topicProperties;


    public KafkaCreateDevProfileEventPublisherPublisher(KafkaTemplate<String, Object> kafkaTemplate, TopicProperties topicProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicProperties = topicProperties;
    }


    @Override
    public void sendMessage(DevProfile profile) {
        final DevProfileCreatedEvent event = new DevProfileCreatedEvent(profile);
        this.kafkaTemplate.send(topicProperties.getTopicName("dev-profile.created"),event).whenComplete(((result, ex) -> {
            if (ex != null) {
                logger.error("Failed to publish event for {}: {}", event.getId(), ex.getMessage());
            } else {
                logger.info("Event published for {}", event.getId());
            }
        }));
    }
}
