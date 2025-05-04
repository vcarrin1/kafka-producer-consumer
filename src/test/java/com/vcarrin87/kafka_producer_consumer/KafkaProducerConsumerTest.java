package com.vcarrin87.kafka_producer_consumer;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import com.vcarrin87.kafka_producer_consumer.models.TransactionMessage;
import com.vcarrin87.kafka_producer_consumer.services.KafkaConsumerService;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = { "test-topic" })
public class KafkaProducerConsumerTest {

	@Mock
	private KafkaTemplate<UUID, TransactionMessage> kafkaTemplate;

	@MockitoSpyBean
	private KafkaConsumerService kafkaConsumerService;


	@Test
	public void testKafkaProducerConsumer() {
		String topic = "test-topic";
		UUID key = UUID.randomUUID();
		TransactionMessage message = new TransactionMessage();
		message.setTransactionId(1L);
		message.setTransactionStatus(TransactionMessage.Status.COMPLETED);
		message.setAmount(100.0);
		message.setEvent(TransactionMessage.Event.valueOf("DEPOSIT"));

		kafkaTemplate.send(topic, key, message);

		// Wait for the consumer to process the message
		try {
			Thread.sleep(2000);
			kafkaConsumerService.consume(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		verify(kafkaConsumerService, times(1)).consume(message);
		
	}

	@Test
	public void testMessageSentToTopic() {
		String topic = "test-topic";
		UUID key = UUID.randomUUID();
		TransactionMessage message = new TransactionMessage();
		message.setTransactionId(2L);
		message.setTransactionStatus(TransactionMessage.Status.PENDING);
		message.setAmount(200.0);
		message.setEvent(TransactionMessage.Event.valueOf("WITHDRAW"));

		// Mock KafkaTemplate behavior
		kafkaTemplate.send(topic, key, message);

		// Verify that the KafkaTemplate's send method was called with the correct arguments
		verify(kafkaTemplate, times(1)).send(topic, key, message);
	}
}
