package com.kb.jess.profiler;

import com.kb.jess.core.model.Account;
import com.kb.jess.core.model.AccountTransactionAggregation;
import com.kb.jess.core.model.Customer;
import com.kb.jess.profiler.config.ProfilerContext;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    protected static ProfilerContext context;
    protected static final long initialAmount = 200000;

    @BeforeAll
    public static void initialize() {
        context = new ProfilerContext();
    }

    public void initializeAllContextBean() {
        context.initialize();
    }

    protected static Customer getDefaultCustomer() {
        return new Customer(getDefaultAccount(), "jess", "1988-07-08", "2019-09-17_00:00:00", 1);
    }

    protected static Account getDefaultAccount() {
        return new Account("1234-1234-1234-1234", initialAmount, AccountTransactionAggregation.empty());
    }
}
