package br.com.gustavoakira.devconnect.adapters.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.kafka")
public class TopicProperties {

    private List<TopicDef> topics;

    public List<TopicDef> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDef> topics) {
        this.topics = topics;
    }

    public static class TopicDef {
        private String name;
        private Integer partitions = 3;
        private Short replicas = 1;
        private Long retentionMs;
        private String cleanupPolicy = "delete";
        private Boolean createDlt = false;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Integer getPartitions() { return partitions; }
        public void setPartitions(Integer partitions) { this.partitions = partitions; }

        public Short getReplicas() { return replicas; }
        public void setReplicas(Short replicas) { this.replicas = replicas; }

        public Long getRetentionMs() { return retentionMs; }
        public void setRetentionMs(Long retentionMs) { this.retentionMs = retentionMs; }

        public String getCleanupPolicy() { return cleanupPolicy; }
        public void setCleanupPolicy(String cleanupPolicy) { this.cleanupPolicy = cleanupPolicy; }

        public Boolean getCreateDlt() { return createDlt; }
        public void setCreateDlt(Boolean createDlt) { this.createDlt = createDlt; }
    }
}
