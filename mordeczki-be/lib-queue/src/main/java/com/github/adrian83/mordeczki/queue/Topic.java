package com.github.adrian83.mordeczki.queue;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class Topic<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Topic.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topicName;

    public Topic(String topicName, KafkaTemplate<String, Object> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<T> send(T message) {
        return kafkaTemplate.send(topicName, message)
                .completable()
                .thenApply(resukt -> {
                    LOGGER.info("Response {}", message);
                    return message;
                });
    }

}
