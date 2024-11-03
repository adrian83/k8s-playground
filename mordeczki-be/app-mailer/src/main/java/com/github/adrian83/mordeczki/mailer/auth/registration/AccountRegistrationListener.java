package com.github.adrian83.mordeczki.mailer.auth.registration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.github.adrian83.mordeczki.mailer.EmailService;
import com.github.adrian83.mordeczki.queue.MessageExtractor;
import com.github.adrian83.mordeczki.queue.message.RegisterAccountMessage;

@Service
public class AccountRegistrationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRegistrationListener.class);

    private static final String QUEUE_TOPIC_NAME_PACEHOLDER = "${kafka.auth.topicRegisteredUser}";

    @Autowired
    private MessageExtractor messageExtractor;

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = QUEUE_TOPIC_NAME_PACEHOLDER)
    public void saveResetPassasswordData(@Payload ConsumerRecord command) {
        LOGGER.info("Received Message: {}", command);
        var msg = messageExtractor.extract(command, RegisterAccountMessage.class);
        LOGGER.info("Sending email: {}", msg);
        //emailService.sendSimpleMessage(msg.email(), "Mordeczki", "Hello from Mordeczki");
        emailService.sendSimpleMessage("adrianbrzoza1983@gmail.com", "Mordeczki", "Hello from Mordeczki");
    }
}
