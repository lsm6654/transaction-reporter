package com.kb.jess.core.support.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.jess.core.exception.TransactionReporterException;
import com.kb.jess.core.model.TransactionBase;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class KafkaJsonDeserializer implements Deserializer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public TransactionBase deserialize(String s, byte[] bytes) {
        final TransactionBase obj;
        try {
            obj = mapper.readValue(bytes, new TypeReference<TransactionBase>() {});
        } catch (Exception e) {
            log.error("error occurred on kafka json deserialization : {}", e.getMessage());
            throw new TransactionReporterException("kafka json deserialization error", e);
        }
        return obj;
    }

    @Override
    public void close() {

    }
}
