package com.github.adrian83.mordeczki.auth.service;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.auth.common.PasswordEncoder;
import com.github.adrian83.mordeczki.auth.exception.InvalidResetPasswordTokenException;
import com.github.adrian83.mordeczki.auth.model.command.RequestResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.model.command.ResetPasswordCommand;
import com.github.adrian83.mordeczki.auth.repository.AccountRepository;
import com.github.adrian83.mordeczki.auth.repository.ResetPasswordRepository;
import com.github.adrian83.mordeczki.queue.MessageExtractor;
import com.github.adrian83.mordeczki.queue.message.ResetPasswordMessage;

@Service
public class PasswordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordService.class);

    private static final String QUEUE_TOPIC_NAME_PACEHOLDER = "${kafka.topicResetPassword}";
    private static final String RESET_PASS_MSG = "reset password data pushed to topic %s with response: %s";

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageExtractor messageExtractor;



    public CompletableFuture<RequestResetPasswordCommand> handleResetPasswordRequest(RequestResetPasswordCommand command) {
        var message = new ResetPasswordMessage(command.email());
        return notificationService.notifyPasswordChangeRequest(message).thenApply(msg -> command);
    }

    @KafkaListener(topics = QUEUE_TOPIC_NAME_PACEHOLDER)
    public void saveResetPassasswordData(@Payload ConsumerRecord command) {
            LOGGER.info("Received Message: {}", command);
            var msg = messageExtractor.extract(command, ResetPasswordMessage.class);
            LOGGER.info("Received Message: {}", msg);

            // var token = UUID.randomUUID().toString();
            // LOGGER.info("Reset password token: " + token);
            // var passReset = accountRepository.findByEmail(command.email())
            //         .map(account -> new ResetPassword(token, account, DateUtil.utcNow()))
            //         .map(resetPasswordRepository::save)
            //         .orElseThrow(() -> new RuntimeException("ups"));
            // LOGGER.info("Reset password: {}", passReset);
    }

    public boolean changePassword(@Payload ResetPasswordCommand command) {
        LOGGER.info("Command: " + command);
        var resetPassData = resetPasswordRepository.findById(command.token())
                .orElseThrow(() -> new InvalidResetPasswordTokenException("invalid token"));

        var newPasswordHash = passwordEncoder.encode(command.password());

        var account = resetPassData.getAccount();
        account.setPasswordHash(newPasswordHash);

        accountRepository.save(account);
        return true;
    }

}
