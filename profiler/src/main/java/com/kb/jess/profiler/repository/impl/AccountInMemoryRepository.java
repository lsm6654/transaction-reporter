package com.kb.jess.profiler.repository.impl;

import com.kb.jess.core.model.Account;
import com.kb.jess.core.model.Customer;
import com.kb.jess.profiler.repository.AbstractInMemoryRepository;
import com.kb.jess.profiler.repository.AccountRepository;

import java.util.Optional;

public class AccountInMemoryRepository extends AbstractInMemoryRepository implements AccountRepository {

    @Override
    public Optional<Account> findAccountByCustomerNumber(Long customerNumber) {
        return findCustomer(customerNumber).map(Customer::getAccount);
    }

    @Override
    public Account saveAccount(Long customerNumber, Account account) {
        final Customer customer = findCustomerByCustomerId(customerNumber);
        final Customer newCustomer = customer.copy(account);
        return saveCustomer(customerNumber, newCustomer).getAccount();
    }


//    @Override
//    public Optional<Account> saveTransactionLog(TransactionLog transactionLog) {
//        final Customer customer = findCustomer(transactionLog.getCustomerNumber()).orElseThrow(() -> new TransactionReporterException("Not found customer"));
//        Customer newCustomer = makeCustomer(transactionLog, customer);
//        saveCustomer(transactionLog.getCustomerNumber(), newCustomer);
//
//        return Optional.of(newCustomer.getAccount());
//    }
//
//    @Override
//    public Optional<Account> saveTransferLog(TransferTransactionLog transferTransactionLog) {
//        final Customer customer = findCustomer(transferTransactionLog.getCustomerNumber()).orElseThrow(() -> new TransactionReporterException("Not found customer"));
//
//        if (Constants.KAKAO_BANK.equals(transferTransactionLog.getSourceBank())) {
//            withdrawAccount(transferTransactionLog.getSourceAccountNumber(), transferTransactionLog.getAmount());
//        } else {
//            //something to do.. with others bank
//        }
//
//        Customer newCustomer = makeCustomer(transferTransactionLog, customer);
//        return Optional.of(newCustomer.getAccount());
//    }
//
//    private Customer makeCustomer(TransactionLog log, Customer customer) {
//        final Account account = customer.getAccount();
//        final long balance = account.getBalance() + log.getAmount();
//        account.getLatestTransactions().add(0, log);
//        final List<TransactionLog> latestTransactionLogs = account.getLatestTransactions().stream().limit(Constants.LATEST_HISTORY_COUNT).collect(Collectors.toList());
//
//        final TransactionLog minTransactionLog = account.getMinTransactionAmountLog().map(i -> getMinimumTransactionAmount(i, log)).orElse(log);
//        final TransactionLog maxTransactionLog = account.getMaxTransactionAmountLog().map(i -> getMaximumTransactionAmount(i, log)).orElse(log);
//
//        final Account newAccount = new Account(log.getAccountNumber(), balance, latestTransactionLogs, minTransactionLog, maxTransactionLog);
//        return customer.copyAndIncrementSessionCount(newAccount);
//    }
//
//    @Override
//    public Optional<Account> findOneByCustomerNumber(Long customerNumber) {
//        return findCustomer(customerNumber).map(Customer::getAccount);
//    }
//
//    private void withdrawAccount(final String accountNumber, final Long amount) {
//        final Long customerNumber = findAccountNumber(accountNumber);
//        final Customer customer = findCustomer(customerNumber).orElseThrow(() -> new TransactionReporterException("Not found customer"));
//        final Account account = customer.getAccount();
//        Long balance = account.getBalance() - amount;
//
//        Account newAccount = account.copy(balance);
//        Customer newCustomer = customer.copy(newAccount);
//        saveCustomer(customerNumber, newCustomer);
//    }
//
//    private TransactionLog getMinimumTransactionAmount(TransactionLog minTransactionLog, TransactionLog transactionLog) {
//        if (minTransactionLog.getAmount() > transactionLog.getAmount()) {
//            return transactionLog;
//        }
//        return minTransactionLog;
//    }
//
//    private TransactionLog getMaximumTransactionAmount(TransactionLog maxTransactionLog, TransactionLog transactionLog) {
//        if (maxTransactionLog.getAmount() < transactionLog.getAmount()) {
//            return transactionLog;
//        }
//        return maxTransactionLog;
//    }
}
