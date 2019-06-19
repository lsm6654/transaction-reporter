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
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        addBean(RandomDataGenerator.class, randomDataGenerator);

        KafkaTransactionProducer kafkaTransactionProducer = KafkaBeanFactory.createTransactionProducer();
        addBean(KafkaTransactionProducer.class, kafkaTransactionProducer);

        LogStoreKafkaService logStoreKafkaService = new LogStoreKafkaService();
        addBean(LogStoreKafkaService.class, logStoreKafkaService);

        GeneratorService generatorService = new GeneratorService();
        addBean(GeneratorService.class, generatorService);
    }
}
