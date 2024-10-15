package com.github.adrian83.mordeczki.mailer.auth.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class AccountRegistrationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRegistrationListener.class);

    private static final String QUEUE_TOPIC_NAME_PACEHOLDER = "${kafka.auth.topicRegisteredUser}";


    @KafkaListener(topics = QUEUE_TOPIC_NAME_PACEHOLDER)
    public void saveResetPassasswordData(@Payload RegisterAccountCommand command) {
        LOGGER.info("Received Message: " + command);
    }
}
