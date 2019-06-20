package com.kb.jess.profiler.repository.impl;

import com.kb.jess.core.model.Account;
import com.kb.jess.core.model.Customer;
import com.kb.jess.profiler.repository.AbstractInMemoryRepository;
import com.kb.jess.profiler.repository.AccountRepository;

import java.util.Optional;

public class AccountInMemoryRepository extends AbstractInMemoryRepository implements AccountRepository {

    @Override
    public Optional<Account> findAccountByCustomerNumber(final Long customerNumber) {
        return findCustomer(customerNumber).map(Customer::getAccount);
    }

    @Override
    public Account saveAccount(final Long customerNumber, final Account account) {
        final Customer customer = findCustomerByCustomerId(customerNumber);
        final Customer newCustomer = customer.copy(account);
        return saveCustomer(customerNumber, newCustomer).getAccount();
    }
}
