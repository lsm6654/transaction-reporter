package com.kb.jess.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kb.jess.core.model.type.TransactionType;

public class CreateAccountLog extends TransactionBase {
    private final String accountNumber;

    @JsonCreator
    public CreateAccountLog(@JsonProperty("accountNumber") String accountNumber,
                            @JsonProperty("customerNumber") long customerNumber,
                            @JsonProperty("transactionTime") String transactionTime) {
        super(customerNumber, transactionTime, TransactionType.CREATE_ACCOUNT);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
