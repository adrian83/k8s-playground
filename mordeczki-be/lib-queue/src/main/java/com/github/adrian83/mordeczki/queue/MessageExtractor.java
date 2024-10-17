package com.github.adrian83.mordeczki.queue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageExtractor  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToJsonSerializer.class);

    private final ObjectMapper objectMapper;

    public MessageExtractor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public <T> T extract(ConsumerRecord record, Class<T> clazz) {
        var val = record.value();
        LOGGER.info("VALUE: {} {}", val.getClass().getSimpleName(), val);

        try {
            return objectMapper.readValue(record.value().toString(), clazz);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

    }

}
