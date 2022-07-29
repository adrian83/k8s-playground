package com.github.adrian83.mordeczki.auth.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyListener.class);

	@KafkaListener(topics = "${topic.registeredUser}")
	public void listenWithHeaders(@Payload String message) {
		LOGGER.info("Received Message: " + message);
	}
}
