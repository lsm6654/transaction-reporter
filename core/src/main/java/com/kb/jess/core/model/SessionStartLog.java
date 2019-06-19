package com.kb.jess.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kb.jess.core.model.type.TransactionType;

public class SessionStartLog extends TransactionBase {

    @JsonCreator
    public SessionStartLog(@JsonProperty("customerNumber") long customerNumber, @JsonProperty("transactionTime") String transactionTime) {
        super(customerNumber, transactionTime, TransactionType.SESSION);
    }
}
