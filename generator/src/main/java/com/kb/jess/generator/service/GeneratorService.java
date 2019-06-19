package com.kb.jess.generator.service;

import com.kb.jess.core.model.TransactionBase;
import com.kb.jess.generator.GeneratorApplication;
import com.kb.jess.generator.config.GeneratorContext;
import com.kb.jess.generator.service.impl.LogStoreKafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GeneratorService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RandomDataGenerator randomDataGenerator;
    private final LogStoreKafkaService logStoreKafkaService;

    public GeneratorService() {
        this.randomDataGenerator = GeneratorContext.getBean(RandomDataGenerator.class);
        this.logStoreKafkaService = GeneratorContext.getBean(LogStoreKafkaService.class);
    }

    public void run() {
        final List<TransactionBase> transactions = randomDataGenerator.generate(100);
        logStoreKafkaService.sendToRepository(transactions);
        log.info("random data generated");
    }

    public void await() throws InterruptedException {
        Thread.sleep(500);
    }

    public void close() {
        logStoreKafkaService.close();
    }
}
