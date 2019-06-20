package com.kb.jess.generator.config;

import com.kb.jess.core.AbstractContext;
import com.kb.jess.core.support.kafka.KafkaBeanFactory;
import com.kb.jess.core.support.kafka.KafkaTransactionProducer;
import com.kb.jess.generator.service.GeneratorService;
import com.kb.jess.generator.service.RandomDataGenerator;
import com.kb.jess.generator.service.impl.LogStoreKafkaService;

public class GeneratorContext extends AbstractContext {

    @Override
    public void initialize() {
        //bean initialize
        final RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        addBean(RandomDataGenerator.class, randomDataGenerator);

        final KafkaTransactionProducer kafkaTransactionProducer = KafkaBeanFactory.createTransactionProducer();
        addBean(KafkaTransactionProducer.class, kafkaTransactionProducer);

        final LogStoreKafkaService logStoreKafkaService = new LogStoreKafkaService();
        addBean(LogStoreKafkaService.class, logStoreKafkaService);

        final GeneratorService generatorService = new GeneratorService();
        addBean(GeneratorService.class, generatorService);
    }
}
