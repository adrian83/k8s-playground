package com.github.adrian83.mordeczki.auth.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.model.command.RegisterCommand;
import com.github.adrian83.mordeczki.auth.model.entity.Account;
import com.github.adrian83.mordeczki.auth.reporting.ReportingService;
import com.github.adrian83.mordeczki.auth.repository.AccountRepository;

@Service
public class RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    private static final String REGISTRATION_MSG = "registration data pushed to topic %s with response: %s";

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ReportingService reportingService;

    @Value(value = "${kafka.topicRegisteredUser}")
    private String registeredUserTopic;

    public CompletableFuture<Object> registerAccount(RegisterCommand command) {
	return kafkaTemplate.send(registeredUserTopic, command).completable().thenApply(result -> {
	    LOGGER.info(REGISTRATION_MSG.formatted(registeredUserTopic, result));
	    return null;
	}).exceptionally((ex) -> {
	    // log
	    reportingService.reportUserRegisteringException(ex);
	    throw new RuntimeException(ex);
	});
    }

    @KafkaListener(topics = "${kafka.topicRegisteredUser}")
    public void saveAccount(@Payload RegisterCommand command) {
	try {
	    LOGGER.info("Received Message: " + command);

	    var encodedPass = passwordService.encode(command.password());
	    var account = Account.newActiveAccount(command.email(), encodedPass);
	    accountRepository.save(account);
	} catch (Exception ex) {
	    reportingService.reportStoringRegistrationDataException(ex);
	}
    }
}
