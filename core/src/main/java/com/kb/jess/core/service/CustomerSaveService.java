package com.kb.jess.core.service;

import com.kb.jess.core.model.CreateAccountLog;
import com.kb.jess.core.model.Customer;
import com.kb.jess.core.model.RegisterLog;
import com.kb.jess.core.model.SessionStartLog;

import java.util.Optional;

public interface CustomerSaveService {
    Optional<Customer> saveSessionLog(SessionStartLog sessionStartLog);

    Optional<Customer> saveRegisterLog(RegisterLog registerLog);

    Optional<Customer> saveCreateAccountLog(CreateAccountLog createAccountLog);
}
