package com.github.adrian83.mordeczki.queue;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToJsonStringDeserializer implements Deserializer<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToJsonStringDeserializer.class);

    @Override
    public String deserialize(String topic, byte[] data) {
        var strVal = new String(data, StandardCharsets.UTF_8);
        LOGGER.info("DESERIALIZE: {}", strVal);
        return strVal;
    }

}
