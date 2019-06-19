package com.kb.jess.core.model;

import com.fasterxml.jackson.annotation.*;
import com.kb.jess.core.model.type.TransactionType;

//Jackson에서 동적으로 subClass deserialization
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateAccountLog.class, name = "create-account"),
        @JsonSubTypes.Type(value = RegisterLog.class, name = "register"),
        @JsonSubTypes.Type(value = SessionStartLog.class, name = "session"),
        @JsonSubTypes.Type(value = TransactionLog.class, name = "transaction"),
        @JsonSubTypes.Type(value = TransferTransactionLog.class, name = "transfer")
})
public abstract class TransactionBase {
    private final long customerNumber;
    private final String transactionTime;
    private final TransactionType transactionType;

    public TransactionBase(final long customerNumber, final String transactionTime, final TransactionType transactionType) {
        this.customerNumber = customerNumber;
        this.transactionTime = transactionTime;
        this.transactionType = transactionType;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public long getCustomerNumber() {
        return customerNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
