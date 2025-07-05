package com.learning.rms.kafka;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.utils.CommonResponse;

@Service
public class KafkaProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, TransactionsDto> kafkaTemplate;

	@Async("asyncExecutor")
	public CompletableFuture<CommonResponse> sendMessage(TransactionsDto transactionsDto) {
		Message<TransactionsDto> message = MessageBuilder.withPayload(transactionsDto)
				.setHeader(KafkaHeaders.TOPIC, "Transactions").build();

		CompletableFuture<SendResult<String, TransactionsDto>> send = kafkaTemplate.send(message);

		CompletableFuture<CommonResponse> resultFuture = new CompletableFuture<>();

		send.whenComplete((result, ex) -> {
			if (ex == null) {
				logger.info(String.format("✅ Message Sent to Kafka -> %s", transactionsDto));
				resultFuture.complete(new CommonResponse("Booking Transaction Done", true, 201));
			} else {
				logger.info("❌ Error sending message to Kafka", ex);
				resultFuture.complete(new CommonResponse("Booking Failed", false, 404));
			}
		});
		return resultFuture;
	}
}