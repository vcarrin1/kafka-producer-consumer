package com.vcarrin87.kafka_producer_consumer.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vcarrin87.kafka_producer_consumer.models.TransactionMessage;
import com.vcarrin87.kafka_producer_consumer.services.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EventController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    /**
     * This endpoint sends a message to the Kafka topic.
     * 
     * @param message The TransactionMessage object to be sent
     * @return ResponseEntity indicating the result of the operation
     */
    @PostMapping("/events/publish-message")
    ResponseEntity<String> sendEvent(@RequestBody TransactionMessage message) {
        UUID uuid = UUID.randomUUID();
        log.info("Sending event with ID: " + message.getTransactionId() + " status: " + message.getTransactionStatus());
        kafkaProducerService.sendMessage("transaction-topic", uuid, message);

        return ResponseEntity.ok("Event sent successfully with ID: " + message.getTransactionId());
    }
}
