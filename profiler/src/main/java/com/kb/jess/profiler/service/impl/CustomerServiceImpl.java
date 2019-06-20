package com.kb.jess.profiler.service.impl;

import com.kb.jess.core.exception.TransactionReporterException;
import com.kb.jess.core.model.*;
import com.kb.jess.profiler.repository.CustomerRepository;
import com.kb.jess.profiler.service.CustomerService;

import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> saveSessionLog(final SessionStartLog sessionStartLog) {
        if (sessionStartLog.getCustomerNumber() < 0) {
            return Optional.empty();
        }

        Customer customer = getCustomer(sessionStartLog.getCustomerNumber());
        customer.incrementSessionCount();
        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> saveRegisterLog(final RegisterLog registerLog) {
        final Customer customer = new Customer(null, registerLog.getCustomerName(), registerLog.getBirthday(), registerLog.getTransactionTime(), 1);
        customerRepository.saveCustomer(registerLog.getCustomerNumber(), customer);
        return Optional.of(customer);
    }

    @Override
    public Optional<Customer> saveCreateAccountLog(final CreateAccountLog createAccountLog) {
        final Customer customer = getCustomer(createAccountLog.getCustomerNumber());
        final Account account = new Account(createAccountLog.getAccountNumber(), 0l, AccountTransactionAggregation.empty());
        final Customer newCustomer = customer.copy(account);

        customerRepository.saveCustomer(createAccountLog.getCustomerNumber(), newCustomer);
        customerRepository.saveCustomerNumber(createAccountLog.getCustomerNumber(), createAccountLog.getAccountNumber());

        return Optional.of(newCustomer);
    }

    @Override
    public Optional<Customer> findCustomer(final Long customerNumber) {
        return customerRepository.findCustomerByCustomerNumber(customerNumber);
    }

    private Customer getCustomer(final Long customerNumber) {
        return findCustomer(customerNumber).orElseThrow(() -> new TransactionReporterException("Not found customer"));
    }
}
