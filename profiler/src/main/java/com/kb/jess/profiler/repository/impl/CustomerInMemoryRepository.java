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

//    @Override
//    public Optional<Customer> saveSessionLog(SessionStartLog sessionStartLog) {
//        if (sessionStartLog.getCustomerNumber() < 0) {
//            return Optional.empty();
//        }
//
//        Customer customer = findCustomerByCustomerId(sessionStartLog.getCustomerNumber());
//        customer.incrementSessionCount();
//
//        return Optional.of(customer);
//    }
//
//    @Override
//    public Optional<Customer> saveRegisterLog(RegisterLog registerLog) {
//        Customer customer = new Customer(null, registerLog.getCustomerName(), registerLog.getBirthday(), registerLog.getTransactionTime(), 1);
//        saveCustomer(registerLog.getCustomerNumber(), customer);
//
//        return Optional.of(customer);
//    }
//
//    @Override
//    public Optional<Customer> saveCreateAccountLog(CreateAccountLog createAccountLog) {
//        Customer customer = findCustomerByCustomerId(createAccountLog.getCustomerNumber());
//
//        Account account = new Account(createAccountLog.getAccountNumber(), 0l, new ArrayList<>(), null, null);
//        Customer newCustomer = customer.copy(account);
//        saveCustomer(createAccountLog.getCustomerNumber(), newCustomer);
//        saveAccountNumber(createAccountLog.getAccountNumber(), createAccountLog.getCustomerNumber());
//
//        return Optional.of(newCustomer);
//    }
//
//    @Override
//    public Optional<Customer> findOneByCustomerNumber(Long customerNumber) {
//        return findCustomer(customerNumber);
//    }
}
