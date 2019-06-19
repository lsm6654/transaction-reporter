package com.kb.jess.core.support.kafka;

import com.kb.jess.core.BaseTest;
import com.kb.jess.core.model.SessionStartLog;
import com.kb.jess.core.support.util.LocalDateTimeUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class KafkaTransactionProducerTest extends BaseTest {

    public static KafkaTransactionProducer kafkaTransactionProducer;

    @BeforeAll
    public static void init() {
        kafkaTransactionProducer = KafkaBeanFactory.createTransactionProducer();
    }

    @Test
    public void sendMessageTransactionallyTest() {
        kafkaTransactionProducer.initTransactions();
        kafkaTransactionProducer.sendMessagesTransactionally("test", Arrays.asList(new SessionStartLog(-1l, LocalDateTimeUtils.nowString())));
        kafkaTransactionProducer.close();
    }
}
