package com.kb.jess.profiler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.jess.core.exception.TransactionReporterException;
import com.kb.jess.core.model.Account;
import com.kb.jess.core.model.Customer;
import com.kb.jess.profiler.service.AccountService;
import com.kb.jess.profiler.service.CustomerService;
import com.kb.jess.core.support.kafka.KafkaTransactionConsumer;
import com.kb.jess.profiler.config.ProfilerContext;
import com.kb.jess.profiler.service.impl.AccountServiceImpl;
import com.kb.jess.profiler.service.impl.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static spark.Spark.*;

public class ProfilerApplication {
    private static final Logger log = LoggerFactory.getLogger(ProfilerApplication.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private static final ProfilerContext profilerContext = new ProfilerContext();

    public static void main(String[] args) {
        profilerContext.initialize();
        registerShutdownHook();
        consumeKafka();
        runApiServer();
    }

    public static void consumeKafka() {
        executorService.execute(() -> {
            KafkaTransactionConsumer consumer = ProfilerContext.getBean(KafkaTransactionConsumer.class);
            consumer.subscribe();
        });
    }

    public static void runApiServer() {
        final CustomerService customerService = ProfilerContext.getBean(CustomerServiceImpl.class);
        final AccountService accountService = ProfilerContext.getBean(AccountServiceImpl.class);
        final ObjectMapper objectMapper = ProfilerContext.getBean(ObjectMapper.class);

        port(8080);
        get("/customers/:customerNumber", (request, response) -> {
            Customer customer = customerService.findCustomer(Long.parseLong(request.params(":customerNumber"))).orElseThrow(() -> new TransactionReporterException("Not found customer"));
            return objectMapper.writeValueAsString(customer);
        });
        get("/customers/:customerNumber/account", (request, response) -> {
            Account account = accountService.findAccountByCustomerNumber(Long.parseLong(request.params(":customerNumber"))).orElseThrow(() -> new TransactionReporterException("Not found Account"));
            return objectMapper.writeValueAsString(account);
        });

        exception(TransactionReporterException.class, (exception, request, response) -> {
            response.status(200);
            response.body(exception.getMessage());
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            response.body(exception.getMessage());
        } );
    }

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("SIGTERM hook");

            KafkaTransactionConsumer consumer = ProfilerContext.getBean(KafkaTransactionConsumer.class);
            consumer.close();

            executorService.shutdown();
            Spark.stop();

            log.info("server stopped");
        }));
    }
}
