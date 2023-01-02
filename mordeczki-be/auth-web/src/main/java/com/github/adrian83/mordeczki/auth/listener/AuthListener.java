package com.github.adrian83.mordeczki.auth.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.service.AuthService;

@Component
public class AuthListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthListener.class);

	@Autowired private AuthService authService;
	
	@KafkaListener(topics = "${kafka.topicRegisteredUser}")
	public void saveAccount(@Payload RegisterCommand command) {
		LOGGER.info("Received Message: " + command);
		authService.saveAccount(command);
	}
}
