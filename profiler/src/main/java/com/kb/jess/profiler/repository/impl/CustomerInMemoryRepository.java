package com.kb.jess.profiler.repository.impl;

import com.kb.jess.core.model.*;
import com.kb.jess.profiler.repository.AbstractInMemoryRepository;
import com.kb.jess.profiler.repository.CustomerRepository;

import java.util.Optional;

public class CustomerInMemoryRepository extends AbstractInMemoryRepository implements CustomerRepository {

    @Override
    public Optional<Customer> findCustomerByCustomerNumber(Long customerNumber) {
        return findCustomer(customerNumber);
    }

    @Override
    public Customer saveCustomer(Long customerNumber, Customer customer) {
        return super.saveCustomer(customerNumber, customer);
    }

    @Override
    public Long findCustomerNumberByAccountNumber(String accountNumber) {
        return findCustomerNumber(accountNumber);
    }

    @Override
    public Long saveCustomerNumber(Long customerNumber, String accountNumber) {
        return saveAccountNumber(accountNumber, customerNumber);
    }
}
