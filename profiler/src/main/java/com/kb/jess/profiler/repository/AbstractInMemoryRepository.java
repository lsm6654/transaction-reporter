package com.kb.jess.profiler.repository;

import com.kb.jess.core.exception.TransactionReporterException;
import com.kb.jess.core.model.Customer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractInMemoryRepository {
    private static final Map<Long, Customer> datasource = new ConcurrentHashMap<>(50000);
    private static final Map<String, Long> accountNumberDatasource = new ConcurrentHashMap<>(50000);

    protected Customer saveCustomer(final Long customerNumber, final Customer customer) {
        datasource.put(customerNumber, customer);
        return customer;
    }

    protected Optional<Customer> findCustomer(final Long customerNumber) {
        return Optional.ofNullable(datasource.get(customerNumber));
    }

    protected Customer findCustomerByCustomerId(final Long customerNumber) {
        return findCustomer(customerNumber).orElseThrow(() -> new TransactionReporterException("Not found customer"));
    }

    protected Long saveAccountNumber(final String accountNumber, final Long customerNumber) {
        accountNumberDatasource.put(accountNumber, customerNumber);
        return customerNumber;
    }

    protected Long findCustomerNumber(final String accountNumber) {
        return accountNumberDatasource.get(accountNumber);
    }
}
