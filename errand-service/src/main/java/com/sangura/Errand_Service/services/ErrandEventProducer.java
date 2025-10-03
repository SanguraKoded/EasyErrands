package com.sangura.Errand_Service.services;

import com.sangura.common.events.DTOs.ErrandEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ErrandEventProducer {

    public static final String TOPIC_CREATED = "errand.created";
    public static final String TOPIC_UPDATED = "errand.updated";
    public static final String TOPIC_DELETED = "errand.deleted";

    private final KafkaTemplate<String, ErrandEvent> kafkaTemplate;
    public KafkaTemplate<String, ErrandEvent> getKafkaTemplate() {
        return this.kafkaTemplate;
    }

    public ErrandEventProducer(KafkaTemplate<String, ErrandEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishErrandCreated(ErrandEvent event) {
        kafkaTemplate.send(TOPIC_CREATED, event.getErrandId().toString(), event);
    }

    public void publishErrandUpdated(ErrandEvent event) {
        kafkaTemplate.send(TOPIC_UPDATED, event.getErrandId().toString(), event);
    }

    public void publishErrandDeleted(ErrandEvent event) {
        kafkaTemplate.send(TOPIC_DELETED, event.getErrandId().toString(), event);
    }
}
