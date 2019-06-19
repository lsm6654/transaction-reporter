package com.kb.jess.profiler.repository;

import com.kb.jess.core.exception.TransactionReporterException;
import com.kb.jess.core.model.Customer;
import com.sun.istack.internal.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractInMemoryRepository {
    private static final Map<Long, Customer> datasource = new ConcurrentHashMap<>(50000);
    private static final Map<String, Long> accountNumberDatasource = new ConcurrentHashMap<>(50000);

    protected Customer saveCustomer(Long customerNumber, Customer customer) {
        datasource.put(customerNumber, customer);
        return customer;
    }

    protected Optional<Customer> findCustomer(Long customerNumber) {
        return Optional.ofNullable(datasource.get(customerNumber));
    }

    @NotNull
    protected Customer findCustomerByCustomerId(Long customerNumber) {
        return findCustomer(customerNumber).orElseThrow(() -> new TransactionReporterException("Not found customer"));
    }

    protected Long saveAccountNumber(String accountNumber, Long customerNumber) {
        accountNumberDatasource.put(accountNumber, customerNumber);
        return customerNumber;
    }

    protected Long findCustomerNumber(String accountNumber) {
        return accountNumberDatasource.get(accountNumber);
    }
}
