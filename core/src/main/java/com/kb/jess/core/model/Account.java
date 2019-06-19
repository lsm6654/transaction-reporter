package com.kb.jess.core.model;

import com.sun.istack.internal.NotNull;

public class Account {
    private final String accountNumber;
    private final Long balance;
    private final AccountTransactionAggregation aggregation;

    public Account(final String accountNumber, final Long balance, final AccountTransactionAggregation aggregation) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.aggregation = aggregation;
    }

    public Long getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @NotNull
    public AccountTransactionAggregation getAggregation() {
        if (aggregation == null) {
            return AccountTransactionAggregation.empty();
        }
        return aggregation;
    }

    public Account copy(final Long balance) {
        return new Account(this.accountNumber, balance, this.aggregation);
    }
}
