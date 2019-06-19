package com.kb.jess.profiler.repository;

import com.kb.jess.core.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findCustomerByCustomerNumber(Long customerNumber);

    Customer saveCustomer(Long customerNumber, Customer customer);

    Long findCustomerNumberByAccountNumber(String accountNumber);

    Long saveCustomerNumber(Long customerNumber, String accountNumber);
}
