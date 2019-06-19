package com.kb.jess.core.support.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.jess.core.exception.TransactionReporterException;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class KafkaJsonSerializer implements Serializer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Object o) {
        final byte[] retVal;
        try {
            retVal = objectMapper.writeValueAsBytes(o);
        } catch (Exception e) {
            log.error("error occurred on kafka json serialization : {}", e.getMessage());
            throw new TransactionReporterException("kafka json serialization error", e);
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
