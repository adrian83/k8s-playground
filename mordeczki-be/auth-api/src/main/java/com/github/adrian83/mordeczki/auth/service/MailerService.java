package com.github.adrian83.mordeczki.auth.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.model.command.RegisterAccountCommand;

@Service
public class MailerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value(value = "${kafka.topicRegisteredUser}")
    private String registeredUserTopic;

    public CompletableFuture<RegisterAccountCommand> accountRegistered(RegisterAccountCommand command) {


        return kafkaTemplate.send(registeredUserTopic, command)
                .completable()
                .thenApply(resukt -> {
                    LOGGER.info("Response {}", command);
                    return command;
                });
    }

}
