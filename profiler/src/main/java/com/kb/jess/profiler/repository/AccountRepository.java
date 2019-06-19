package com.kb.jess.profiler.repository;

import com.kb.jess.core.model.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findAccountByCustomerNumber(Long customerNumber);

    Account saveAccount(Long customerNumber, Account account);
}
