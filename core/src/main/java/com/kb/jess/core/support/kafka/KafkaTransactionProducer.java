package com.kb.jess.core.support.kafka;

import com.kb.jess.core.model.TransactionBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class KafkaTransactionProducer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final KafkaProducer<Long, TransactionBase> kafkaProducer;

    public KafkaTransactionProducer(final KafkaProducer<Long, TransactionBase> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        kafkaProducer.initTransactions();
    }

    public void initTransactions() {
        kafkaProducer.initTransactions();
    }

    public void close() {
        kafkaProducer.close();
    }

    //support only transactional sending method.
    public void sendMessagesTransactionally(final String topic, final List<TransactionBase> messages) {
        kafkaProducer.beginTransaction();
        try {
            sendMessages(topic, messages);
            kafkaProducer.commitTransaction();
        } catch (Exception e) {
            kafkaProducer.abortTransaction();
            log.error("kafka producing error : {}", e.getMessage());
            //TODO retry producing & error handling
        }
    }

    private void sendMessages(final String topic, final List<TransactionBase> messages) {
        for (final TransactionBase message: messages) {
//            List<Header> headers = new ArrayList<>();
//            headers.add(new RecordHeader("message-type", message.getTransactionType().toString().getBytes()));

            final ProducerRecord<Long, TransactionBase> record = new ProducerRecord<>(topic, message.getCustomerNumber(), message);
            record.headers().add(new RecordHeader("message-type", message.getTransactionType().toString().getBytes()));

            kafkaProducer.send(record);
        }
    }
}
