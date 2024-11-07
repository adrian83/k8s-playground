package com.github.adrian83.mordeczki.mailer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.adrian83.mordeczki.queue.MessageExtractor;
import com.github.adrian83.mordeczki.queue.QueueConfig;

@Configuration
public class KafkaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

    private static final String GROUP_ID = "app-mailer";

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.auth.topicRegisteredUser}")
    private String registeredUserTopic;

    @Value(value = "${kafka.auth.topicResetPassword}")
    private String resetPasswordTopic;

    @Bean
    KafkaAdmin createKafkaAdmin() {
        LOGGER.info("Connecting to kafka: " + bootstrapAddress);
        return QueueConfig.createKafkaAdmin(bootstrapAddress);
    }

    @Bean
    public KafkaAdmin.NewTopics initKafkaTopics() {
        return new NewTopics(
                QueueConfig.newTopic(resetPasswordTopic),
                QueueConfig.newTopic(registeredUserTopic)
        );
    }

    @Bean
    ConsumerFactory<String, String> createKafkaConsumerFactory() {
        return QueueConfig.createConsumerFactory(bootstrapAddress, GROUP_ID);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> createKafkaListenerContainerFactory(
            @Autowired ConsumerFactory<String, String> consumerFactory) {
        return QueueConfig.createKafkaListenerContainerFactory(consumerFactory);
    }

    @Bean
    public MessageExtractor createMessageExtractor(@Autowired ObjectMapper objectMapper) {
        return new MessageExtractor(objectMapper);
    }
}
