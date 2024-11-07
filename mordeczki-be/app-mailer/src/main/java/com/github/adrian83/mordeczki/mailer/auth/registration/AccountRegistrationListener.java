package com.github.adrian83.mordeczki.mailer.auth.registration;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

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

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @KafkaListener(topics = QUEUE_TOPIC_NAME_PACEHOLDER)
    public void saveResetPassasswordData(@Payload ConsumerRecord record) {
        try {
            var msg = messageExtractor.extract(record, RegisterAccountMessage.class);

            Map<String, Object> model = new HashMap<>();
            model.put("email", msg.email());
            model.put("token", msg.token());

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(model);

            String htmlBody = springTemplateEngine.process("auth-register-account.html", thymeleafContext);

            emailService.sendHtmlEmail("adrianbrzoza1983@gmail.com", "Mordeczki", htmlBody);

        } catch (MessagingException ex) {
            LOGGER.error("ERROR: {}", ex);
        }

    }

}
