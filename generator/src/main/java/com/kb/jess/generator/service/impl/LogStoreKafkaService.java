package com.kb.jess.generator.service.impl;

import com.kb.jess.core.model.TransactionBase;
import com.kb.jess.core.support.PropertiesHelper;
import com.kb.jess.core.support.kafka.KafkaTransactionProducer;
import com.kb.jess.generator.config.GeneratorContext;
import com.kb.jess.generator.service.LogStoreService;

import java.util.List;

public class LogStoreKafkaService implements LogStoreService {
    private final KafkaTransactionProducer kafkaTransactionProducer = GeneratorContext.getBean(KafkaTransactionProducer.class);
    private final String topic;

    public LogStoreKafkaService() {
        this.topic = PropertiesHelper.getProperty("kafka.producer.topic");
    }

    @Override
    public void sendToRepository(List<TransactionBase> transactions) {
        kafkaTransactionProducer.sendMessagesTransactionally(topic, transactions);
    }

    @Override
    public void close() {
        kafkaTransactionProducer.close();
    }
}
