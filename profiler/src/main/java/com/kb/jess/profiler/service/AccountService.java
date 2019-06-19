package com.kb.jess.profiler.service;

import com.kb.jess.core.model.Account;
import com.kb.jess.core.service.AccountSaveService;

import java.util.Optional;

public interface AccountService extends AccountSaveService {
    Optional<Account> findAccountByCustomerNumber(Long customerNumber);
}
