package br.com.gustavoakira.devconnect.adapters.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.errors.TopicExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Component
public class TopicManager {

    private static final Logger log = LoggerFactory.getLogger(TopicManager.class);

    private final TopicProperties topicsProperties;
    private final AdminClient adminClient;

    public TopicManager(TopicProperties topicsProperties, KafkaAdmin kafkaAdmin) {
        this.topicsProperties = topicsProperties;
        this.adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ensureTopicsExist() {
        final List<TopicProperties.TopicDef> defs = topicsProperties.getTopics();
        if (defs == null || defs.isEmpty()) {
            log.info("Nenhum tópico configurado em app.kafka.topics");
            return;
        }

        try {
            final Set<String> existing = listExistingTopics();

            for (TopicProperties.TopicDef def : defs) {
                final String name = def.getName();
                if (existing.contains(name)) {
                    log.info("Tópico já existe: {}", name);
                    continue;
                }

                createTopic(def);
                if (Boolean.TRUE.equals(def.getCreateDlt())) {
                    createDlt(def);
                }
            }
        } catch (Exception e) {
            log.error("Erro ao garantir existência de tópicos", e);
        }
    }

    private Set<String> listExistingTopics() throws ExecutionException, InterruptedException {
        final ListTopicsOptions opts = new ListTopicsOptions();
        opts.listInternal(false);
        final KafkaFuture<Set<String>> future = adminClient.listTopics(opts).names();
        return future.get();
    }

    private void createTopic(TopicProperties.TopicDef def) {
        final String name = def.getName();
        final NewTopic topic = getNewTopic(def, name);

        final CreateTopicsResult result = adminClient.createTopics(Collections.singletonList(topic));
        try {
            result.values().get(name).get();
            log.info("Tópico criado com sucesso: {} (partitions={}, replicas={})",
                    name, def.getPartitions(), def.getReplicas());
        } catch (ExecutionException ex) {
            if (ex.getCause() instanceof TopicExistsException) {
                log.warn("Tópico já existia ao tentar criar (race): {}", name);
            } else {
                log.error("Falha ao criar tópico {}", name, ex.getCause());
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.error("Criação de tópico interrompida: {}", name, ex);
        }
    }

    private static NewTopic getNewTopic(TopicProperties.TopicDef def, String name) {
        final NewTopic topic = new NewTopic(name, def.getPartitions(), def.getReplicas());

        final Map<String, String> configs = new HashMap<>();
        if (def.getRetentionMs() != null) {
            configs.put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(def.getRetentionMs()));
        }
        if (def.getCleanupPolicy() != null) {
            configs.put(TopicConfig.CLEANUP_POLICY_CONFIG, def.getCleanupPolicy());
        }
        if (!configs.isEmpty()) {
            topic.configs(configs);
        }
        return topic;
    }

    private void createDlt(TopicProperties.TopicDef def) {
        final String dltName = def.getName() + ".dlt";
        try {
            final Set<String> existing = listExistingTopics();
            if (existing.contains(dltName)) {
                log.info("DLT já existe: {}", dltName);
                return;
            }

            final NewTopic dlt = new NewTopic(dltName, 1, (short) 1);
            final Map<String, String> cfg = new HashMap<>();
            cfg.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_COMPACT);
            dlt.configs(cfg);

            final CreateTopicsResult result = adminClient.createTopics(Collections.singletonList(dlt));
            result.values().get(dltName).get();
            log.info("Dead Letter Topic criado: {}", dltName);
        } catch (Exception ex) {
            log.error("Erro ao criar DLT {}", dltName, ex);
        }
    }
}
