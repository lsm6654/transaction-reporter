package com.kb.jess.profiler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.kb.jess.core.AbstractContext;
import com.kb.jess.core.support.kafka.KafkaBeanFactory;
import com.kb.jess.core.support.kafka.KafkaTransactionConsumer;
import com.kb.jess.profiler.repository.impl.AccountInMemoryRepository;
import com.kb.jess.profiler.repository.impl.CustomerInMemoryRepository;
import com.kb.jess.profiler.service.impl.AccountServiceImpl;
import com.kb.jess.profiler.service.impl.CustomerServiceImpl;

public class ProfilerContext extends AbstractContext {

    @Override
    public void initialize() {
        //bean initialize
        final AccountInMemoryRepository accountRepository = new AccountInMemoryRepository();
        final CustomerInMemoryRepository customerRepository = new CustomerInMemoryRepository();
        addBean(AccountInMemoryRepository.class, accountRepository);
        addBean(CustomerInMemoryRepository.class, customerRepository);

        final AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, customerRepository);
        final CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository);
        addBean(AccountServiceImpl.class, accountService);
        addBean(CustomerServiceImpl.class, customerService);

        final KafkaTransactionConsumer transactionConsumer = KafkaBeanFactory.createTransactionConsumer(accountService, customerService);
        addBean(KafkaTransactionConsumer.class, transactionConsumer);

        addBean(ObjectMapper.class, objectMapper());
    }

    private ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());

        return objectMapper;
    }
}
