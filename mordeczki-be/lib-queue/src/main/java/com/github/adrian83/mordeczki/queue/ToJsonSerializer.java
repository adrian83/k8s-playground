package com.github.adrian83.mordeczki.queue;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ToJsonSerializer implements Serializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToJsonSerializer.class);

    private final ObjectMapper objectMapper;

    public ToJsonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        LOGGER.info("SERIALIZE: {} {}", data.getClass().getSimpleName(), data);
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
