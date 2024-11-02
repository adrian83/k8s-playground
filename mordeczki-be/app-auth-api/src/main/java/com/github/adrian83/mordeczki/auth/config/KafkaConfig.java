package com.github.adrian83.mordeczki.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.adrian83.mordeczki.queue.MessageExtractor;
import com.github.adrian83.mordeczki.queue.QueueConfig;
import com.github.adrian83.mordeczki.queue.Topic;
import com.github.adrian83.mordeczki.queue.message.RegisterAccountMessage;
import com.github.adrian83.mordeczki.queue.message.ResetPasswordMessage;

@Configuration
public class KafkaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

    private static final String GROUP_ID = "app-auth-api";

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topicRegisteredUser}")
    private String registeredUserTopic;

    @Value(value = "${kafka.topicResetPassword}")
    private String resetPasswordTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
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
    public ProducerFactory<String, Object> producerFactory(@Autowired ObjectMapper objectMapper
    ) {
        return QueueConfig.createProducerFactory(objectMapper, bootstrapAddress);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(@Autowired ProducerFactory<String, Object> producerFactory
    ) {
        return QueueConfig.createKafkaTemplate(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return QueueConfig.createConsumerFactory(bootstrapAddress, GROUP_ID);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            @Autowired ConsumerFactory<String, String> consumerFactory
    ) {
        return QueueConfig.createKafkaListenerContainerFactory(consumerFactory);
    }

    @Bean
    @Qualifier("registeredUserTopic")
    Topic<RegisterAccountMessage> createRegisteredUsersTopic(@Autowired KafkaTemplate<String, Object> kafkaTemplate
    ) {
        return new Topic<>(registeredUserTopic, kafkaTemplate);
    }

    @Bean
    @Qualifier("resetPasswordTopic")
    Topic<ResetPasswordMessage> createResetPasswordTopic(@Autowired KafkaTemplate<String, Object> kafkaTemplate
    ) {
        return new Topic<>(resetPasswordTopic, kafkaTemplate);
    }

    @Bean
    public MessageExtractor createMessageExtractor(@Autowired ObjectMapper objectMapper
    ) {
        return new MessageExtractor(objectMapper);
    }

}
