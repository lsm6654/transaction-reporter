package com.kb.jess.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kb.jess.core.model.type.TransactionType;

public class TransferTransactionLog extends TransactionLog {
    private final String sourceAccountNumber;
    private final String sourceAccountName;
    private final String sourceBank;

    @JsonCreator
    public TransferTransactionLog(@JsonProperty("sourceBank") String sourceBank,
                                  @JsonProperty("sourceAccountNumber") String sourceAccountNumber,
                                  @JsonProperty("sourceAccountName") String sourceAccountName,
                                  @JsonProperty("accountNumber") String accountNumber,
                                  @JsonProperty("amount") long amount,
                                  @JsonProperty("customerNumber") long customerNumber,
                                  @JsonProperty("transactionTime") String transactionTime) {
        super(TransactionType.TRANSFER, accountNumber, amount, customerNumber, transactionTime);
        this.sourceBank = sourceBank;
        this.sourceAccountNumber = sourceAccountNumber;
        this.sourceAccountName = sourceAccountName;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getSourceAccountName() {
        return sourceAccountName;
    }

    public String getSourceBank() {
        return sourceBank;
    }
}
