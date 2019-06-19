package com.kb.jess.core.model;

import java.util.concurrent.atomic.AtomicLong;

public class Customer {
    private final Account account;
    private final String name;
    private final String birthday;
    private final String registerTime;
    private final AtomicLong sessionCount;

    public Customer(final Account account, final String name, final String birthday, final String registerTime, long sessionCount) {
        this.account = account;
        this.name = name;
        this.birthday = birthday;
        this.registerTime = registerTime;
        this.sessionCount = new AtomicLong(sessionCount);
    }

    public Customer(final Account account, final String name, final String birthday, final String registerTime, final AtomicLong sessionCount) {
        this.account = account;
        this.name = name;
        this.birthday = birthday;
        this.registerTime = registerTime;
        this.sessionCount = sessionCount;
    }

    public long incrementSessionCount() {
        return sessionCount.incrementAndGet();
    }

    public Account getAccount() {
        return account;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public AtomicLong getSessionCount() {
        return sessionCount;
    }

    public Customer copyAndIncrementSessionCount(Account account) {
        this.incrementSessionCount();
        return copy(account);
    }

    public Customer copy(Account account) {
        return new Customer(account, this.name, this.birthday, this.registerTime, this.sessionCount);
    }

    public String getName() {
        return name;
    }
}
