package com.kb.jess.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kb.jess.core.model.type.TransactionType;

public class TransactionLog extends TransactionBase {
    private final String accountNumber;
    private final long amount;

    @JsonCreator
    public TransactionLog(@JsonProperty("transactionType") TransactionType transactionType,
                          @JsonProperty("accountNumber") String accountNumber,
                          @JsonProperty("amount") long amount,
                          @JsonProperty("customerNumber") long customerNumber,
                          @JsonProperty("transactionTime") String transactionTime){
        super(customerNumber, transactionTime, transactionType);
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
