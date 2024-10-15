package com.github.adrian83.mordeczki.auth.config;

import org.apache.kafka.clients.admin.NewTopic;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.github.adrian83.mordeczki.queue.QueueConfig;


@Configuration
public class KafkaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topicRegisteredUser}")
    private String registeredUserTopic;

    @Value(value = "${kafka.topicResetPassword}")
    private String resetPasswordTopic;

    @Bean
    KafkaAdmin kafkaAdmin() {
        LOGGER.info("Connecting to kafka: " + bootstrapAddress);
        return QueueConfig.createKafkaAdmin(bootstrapAddress);
    }

    @Bean
    @Qualifier("registeredUserTopic")
    NewTopic registeredUsersTopic() {
        return new NewTopic(registeredUserTopic, 1, (short) 1);
    }

    @Bean
    @Qualifier("resetPasswordTopic")
    NewTopic resetPasswordTopic() {
        return new NewTopic(resetPasswordTopic, 1, (short) 1);
    }

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return QueueConfig.createProducerFactory(bootstrapAddress);
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate(@Autowired ProducerFactory<String, Object> producerFactory) {
        return QueueConfig.createKafkaTemplate(producerFactory);
    }

    @Bean
    ConsumerFactory<String, Object> consumerFactory() {
        return QueueConfig.createConsumerFactory(bootstrapAddress);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(@Autowired ConsumerFactory<String, Object> consumerFactory) {
        return QueueConfig.createKafkaListenerContainerFactory(consumerFactory);
    }
}
