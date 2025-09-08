package com.sangura.common.events.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaTopicConfig {
    @Bean
    public NewTopic assignmentEventsTopic(){
        return TopicBuilder.name("assignment-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic errandEventsTopic(){
        return TopicBuilder.name("errand-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic trackingEventsTopic(){
        return TopicBuilder.name("tracking-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
