package com.kb.jess.core.service;

import com.kb.jess.core.model.Account;
import com.kb.jess.core.model.TransactionLog;
import com.kb.jess.core.model.TransferTransactionLog;

import java.util.Optional;

public interface AccountSaveService {
    Optional<Account> saveTransactionLog(TransactionLog transactionLog);

    Optional<Account> saveTransferLog(TransferTransactionLog transferTransactionLog);
}
