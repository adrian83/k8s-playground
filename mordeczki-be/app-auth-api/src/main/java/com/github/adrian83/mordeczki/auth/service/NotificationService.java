package com.github.adrian83.mordeczki.auth.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.queue.Topic;
import com.github.adrian83.mordeczki.queue.message.RegisterAccountMessage;
import com.github.adrian83.mordeczki.queue.message.ResetPasswordMessage;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    @Qualifier("registeredUserTopic")
    private Topic<RegisterAccountMessage> registeredAccountTopic;

    @Autowired
    @Qualifier("resetPasswordTopic")
    private Topic<ResetPasswordMessage> resetPasswordTopic;

    public CompletableFuture<RegisterAccountMessage> notifyAccountCreation(RegisterAccountMessage message) {
        LOGGER.info("New account created notification: {}", message);
        return registeredAccountTopic.send(message);
    }

    public CompletableFuture<ResetPasswordMessage> notifyPasswordChangeRequest(ResetPasswordMessage message) {
        LOGGER.info("New reset password request notification: {}", message);
        return resetPasswordTopic.send(message);
    }
}
