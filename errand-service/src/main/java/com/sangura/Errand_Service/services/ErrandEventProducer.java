package com.sangura.Errand_Service.services;

import com.sangura.common.events.DTOs.ErrandEvent;
import com.sangura.common.events.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ErrandEventProducer {

    private final KafkaTemplate<String, ErrandEvent> kafkaTemplate;

    public ErrandEventProducer(KafkaTemplate<String, ErrandEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void publishErrandCreated(ErrandEvent event){
        kafkaTemplate.send(KafkaTopics.Errand_Created,String.valueOf(event.getErrandId()), event);
    }
    public void publishErrandUpdated(ErrandEvent event){
        kafkaTemplate.send(KafkaTopics.Errand_Updated,String.valueOf(event.getErrandId()), event);
    }
    public void publishErrandDeleted(ErrandEvent event){
        kafkaTemplate.send(KafkaTopics.Errand_Deleted,String.valueOf(event.getErrandId()), event);
    }
}
