package com.kb.jess.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kb.jess.core.model.type.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegisterLog extends TransactionBase {
    private final String customerName;
    private final String birthday;

    @JsonCreator
    public RegisterLog(@JsonProperty("customerNumber") long customerNumber,
                       @JsonProperty("customerName") String customerName,
                       @JsonProperty("birthday") String birthday,
                       @JsonProperty("transactionTime") String transactionTime) {
        super(customerNumber, transactionTime, TransactionType.REGISTER);
        this.customerName = customerName;
        this.birthday = birthday;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getBirthday() {
        return birthday;
    }
}
