package com.vcarrin87.kafka_producer_consumer.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.vcarrin87.kafka_producer_consumer.models.TransactionMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<UUID, TransactionMessage> kafkaTemplate;
   
    public void sendMessage(String topic, UUID key, TransactionMessage message) {
        var future = kafkaTemplate.send(topic, key, message);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error sending message: {}", ex.getMessage());
                future.completeExceptionally(ex);
            } else {
                log.info("Message sent successfully: {} for topic {}", message, topic);
                future.complete(result);
            }
        });
    }
}
