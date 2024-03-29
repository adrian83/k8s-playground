package com.github.adrian83.mordeczki.auth.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.exception.InvalidResetPasswordTokenException;
import com.github.adrian83.mordeczki.auth.model.command.RequestResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.model.command.ResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.model.entity.ResetPassword;
import com.github.adrian83.mordeczki.auth.reporting.ReportingService;
import com.github.adrian83.mordeczki.auth.repository.AccountRepository;
import com.github.adrian83.mordeczki.auth.repository.ResetPasswordRepository;

@Service
public class ResetPasswordService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordService.class);

	private static final String QUEUE_TOPIC_NAME_PACEHOLDER = "${kafka.topicResetPassword}";
	private static final String RESET_PASS_MSG = "reset password data pushed to topic %s with response: %s";

	@Autowired
	private ResetPasswordRepository resetPasswordRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	@Autowired
	private ReportingService reportingService;
	@Autowired
	private PasswordService passwordService;

	@Value(value = QUEUE_TOPIC_NAME_PACEHOLDER)
	private String resetPasswordTopic;

	public CompletableFuture<Object> handleResetPasswordRequest(RequestResetPasswordCommand command) {
		return kafkaTemplate.send(resetPasswordTopic, command).completable().thenApply(result -> {
			LOGGER.info(RESET_PASS_MSG.formatted(resetPasswordTopic, result));
			return null;
		}).exceptionally((ex) -> {
			// log
			reportingService.reportResetPasswordException(ex);
			throw new RuntimeException(ex);
		});
	}

	@KafkaListener(topics = QUEUE_TOPIC_NAME_PACEHOLDER)
	public void saveResetPassasswordData(@Payload RequestResetPasswordCommand command) {
		try {
			LOGGER.info("Received Message: " + command);
			var passReset = accountRepository.findById(command.email())
					.map(account -> new ResetPassword(UUID.randomUUID().toString(), account, LocalDateTime.now()))
					.map(resetPasswordRepository::save).orElseThrow(() -> new RuntimeException("ups"));
			LOGGER.info("Reset password: {}", passReset);
		} catch (Exception ex) {
			// log
			reportingService.reportStoringResetPasswordDataException(ex);
			throw new RuntimeException(ex);
		}
	}

	public boolean changePassword(@Payload ResetPasswordCommand command) {
		LOGGER.info("Command: " + command);
		var resetPassData = resetPasswordRepository.findById(command.token())
				.orElseThrow(() -> new InvalidResetPasswordTokenException("invalid token"));

		var newPasswordHash = passwordService.encode(command.password());

		var account = resetPassData.getAccount();
		account.setPasswordHash(newPasswordHash);

		accountRepository.save(account);
		return true;
	}

}
