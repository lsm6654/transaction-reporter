package com.kb.jess.profiler.service;

import com.kb.jess.core.model.Customer;
import com.kb.jess.core.service.CustomerSaveService;

import java.util.Optional;

public interface CustomerService extends CustomerSaveService {
    Optional<Customer> findCustomer(Long customerNumber);
}
