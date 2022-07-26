package com.github.adrian83.mordeczki.auth.config;

import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class QueueConfig {
	
    @Bean
    @Primary
    KafkaAdmin kafkaAdmin() {
        return mock(KafkaAdmin.class);
    }
	
	@Bean
    @Primary
    @SuppressWarnings("unchecked")
    ProducerFactory<String, Object> producerFactory() {
        return mock(DefaultKafkaProducerFactory.class);
    }

	@Bean
    @Primary
    @SuppressWarnings("unchecked")
    KafkaTemplate<String, Object> kafkaTemplate(@Autowired ProducerFactory<String, Object> producerFactory) {
        return mock(KafkaTemplate.class);
    }
}
