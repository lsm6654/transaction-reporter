package com.kb.jess.profiler.service.impl;

import com.kb.jess.core.exception.TransactionReporterException;
import com.kb.jess.core.model.*;
import com.kb.jess.profiler.repository.AccountRepository;
import com.kb.jess.profiler.repository.CustomerRepository;
import com.kb.jess.profiler.service.AccountService;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Account> saveTransactionLog(TransactionLog transactionLog) {
        final Customer customer = findCustomer(transactionLog.getCustomerNumber());
        final Account newAccount = makeAccount(transactionLog, customer);

        accountRepository.saveAccount(transactionLog.getCustomerNumber(), newAccount);
        return Optional.of(newAccount);
    }

    @Override
    public Optional<Account> saveTransferLog(TransferTransactionLog transferTransactionLog) {
        final Customer customer = findCustomer(transferTransactionLog.getCustomerNumber());
        final Account newAccount = makeAccount(transferTransactionLog, customer);

        if (Constants.KAKAO_BANK.equals(transferTransactionLog.getSourceBank())) {
            withdrawAccount(transferTransactionLog.getSourceAccountNumber(), transferTransactionLog.getAmount());
        } else {
            //something to do.. with others bank
        }

        accountRepository.saveAccount(transferTransactionLog.getCustomerNumber(), newAccount);
        return Optional.of(newAccount);
    }

    @Override
    public Optional<Account> findAccountByCustomerNumber(Long customerNumber) {
        return accountRepository.findAccountByCustomerNumber(customerNumber);
    }

    private Account makeAccount(TransactionLog log, Customer customer) {
        final Account account = customer.getAccount();
        final long balance = account.getBalance() + log.getAmount();
        final AccountTransactionAggregation aggregation = account.getAggregation().newAggregation(log);

        //TODO
        return new Account(log.getAccountNumber(), balance, aggregation);
    }

    private void withdrawAccount(final String accountNumber, final Long amount) {
        final Long customerNumber = customerRepository.findCustomerNumberByAccountNumber(accountNumber);
        final Customer customer = findCustomer(customerNumber);
        final Account account = customer.getAccount();
        Long balance = account.getBalance() - amount;

        final Account newAccount = account.copy(balance);
        final Customer newCustomer = customer.copy(newAccount);
        customerRepository.saveCustomer(customerNumber, newCustomer);
    }

    private Customer findCustomer(Long customerNumber) {
        return customerRepository.findCustomerByCustomerNumber(customerNumber).orElseThrow(() -> new TransactionReporterException("Not found customer"));
    }
}
