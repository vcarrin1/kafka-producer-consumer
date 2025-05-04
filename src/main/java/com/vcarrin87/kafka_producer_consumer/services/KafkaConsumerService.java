package com.vcarrin87.kafka_producer_consumer.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.vcarrin87.kafka_producer_consumer.models.TransactionMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "transaction-topic", groupId = "group_id")
    public void consume(TransactionMessage message) {
        log.info("Message received: {}", message);
    }
}
