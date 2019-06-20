package com.kb.jess.core.support.kafka;

import com.kb.jess.core.model.*;
import com.kb.jess.core.service.AccountSaveService;
import com.kb.jess.core.service.CustomerSaveService;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class KafkaTransactionConsumer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final KafkaConsumer<Long, TransactionBase> kafkaConsumer;
    private final List<String> topics;
    private final AccountSaveService accountSaveService;
    private final CustomerSaveService customerSaveService;

    public KafkaTransactionConsumer(final KafkaConsumer<Long, TransactionBase> kafkaConsumer, final List<String> topics,
                                    final AccountSaveService accountSaveService, final CustomerSaveService customerSaveService) {
        this.kafkaConsumer = kafkaConsumer;
        this.topics = topics;
        this.accountSaveService = accountSaveService;
        this.customerSaveService = customerSaveService;
    }

    public void subscribe() {
        log.info("kafka subscribe on");
        kafkaConsumer.subscribe(topics);

        try {
            while(true) {
                consume();
            }
        } catch (WakeupException e) {
            log.info("occurred kafka wakeup signal");
            kafkaConsumer.close();
        }
    }

    private void consume() {
        try {
            final ConsumerRecords<Long, TransactionBase> records = kafkaConsumer.poll(Duration.ofMillis(1000));
            records.forEach(r -> {
                switch (r.value().getTransactionType()) {
                    case SESSION:
                        customerSaveService.saveSessionLog((SessionStartLog) r.value());
                        break;
                    case REGISTER:
                        customerSaveService.saveRegisterLog((RegisterLog) r.value());
                        break;
                    case CREATE_ACCOUNT:
                        customerSaveService.saveCreateAccountLog((CreateAccountLog) r.value());
                        break;
                    case DEPOSIT:
                    case WITHDRAWL:
                        accountSaveService.saveTransactionLog((TransactionLog) r.value());
                        break;
                    case TRANSFER:
                        accountSaveService.saveTransferLog((TransferTransactionLog) r.value());
                        break;
                }
            });
        } catch (Exception e) {
            log.error("occurred error when kafka consume : {}", e.getMessage(), e);
        }
    }

    public void close() {
        kafkaConsumer.wakeup();
    }
}
