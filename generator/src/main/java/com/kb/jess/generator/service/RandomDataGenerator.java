package com.kb.jess.generator.service;

import com.kb.jess.core.model.*;
import com.kb.jess.core.model.type.TransactionType;
import com.kb.jess.core.support.util.LocalDateTimeUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator {
    private final Account[] accounts = new Account[Constants.MAX_CUSTOMER_COUNT];
    private final long minEpochTime = LocalDate.of(1920, 1, 1).toEpochDay();
    private final long maxEpochTime = LocalDate.of(1999, 1, 1).toEpochDay();

    public List<TransactionBase> generate(final int concurrentUserCount) {
        final Set<Integer> customerNumbers = new HashSet<>();
        while(customerNumbers.size() < concurrentUserCount) {
            customerNumbers.add(ThreadLocalRandom.current().nextInt(Constants.MAX_CUSTOMER_COUNT));
        }

        final List<TransactionBase> transactions = new ArrayList<>();
        for (Integer customerNumber: customerNumbers) {
            final List<TransactionBase> session = accounts[customerNumber] == null ? generateRegisterData(customerNumber) : generateTransactionData(customerNumber);
            transactions.addAll(session);
        }

        return transactions;
    }

    private List<TransactionBase> generateRegisterData(final Integer customerNumber) {
        final String randomDate = LocalDateTimeUtils.getRandomDateString(minEpochTime, maxEpochTime);
        final String accountNumber = UUID.randomUUID().toString();
        accounts[customerNumber] = new Account(accountNumber);

        final SessionStartLog sessionStartLog = new SessionStartLog(-1l, LocalDateTimeUtils.nowString());
        final RegisterLog registerLog = new RegisterLog(customerNumber, "USER" + customerNumber, randomDate, LocalDateTimeUtils.nowString());
        final CreateAccountLog createAccountLog = new CreateAccountLog(accountNumber, customerNumber, LocalDateTimeUtils.nowString());

        return Arrays.asList(sessionStartLog, registerLog, createAccountLog);
    }

    private List<TransactionBase> generateTransactionData(final Integer customerNumber) {
        final List<TransactionBase> transactions = new ArrayList<>();
        transactions.add(new SessionStartLog(customerNumber, LocalDateTimeUtils.nowString()));
        transactions.add(getRandomTransaction(customerNumber));

        return transactions;
    }

    private TransactionBase getRandomTransaction(final Integer customerNumber) {
        final Account accountInfo = accounts[customerNumber];
        final int amount = ThreadLocalRandom.current().nextInt(10000000) + 1;

        int random = ThreadLocalRandom.current().nextInt(3);
        if (accountInfo.getBalance() - amount < 0) {
            random = 2;
        }

        switch (random) {
            case 0 :
                //출금
                accountInfo.addAmount(-amount);
                return new TransactionLog(TransactionType.WITHDRAWL, accountInfo.getNumber(), -amount, customerNumber, LocalDateTimeUtils.nowString());
            case 1 :
                //계좌 이체
                accountInfo.addAmount(-amount);
                final int i = ThreadLocalRandom.current().nextInt(Constants.BANK_NAMES.size());
                return new TransferTransactionLog(Constants.BANK_NAMES.get(i), accountInfo.getNumber(), "USER" + System.currentTimeMillis(), accountInfo.getNumber(), amount, customerNumber, LocalDateTimeUtils.nowString());
            case 2 :
            default:
                //입금
                accountInfo.addAmount(amount);
                return new TransactionLog(TransactionType.DEPOSIT, accountInfo.getNumber(), amount, customerNumber, LocalDateTimeUtils.nowString());
        }
    }

    private static class Account {
        private final String number;
        private long balance;

        public Account(String number) {
            this.number = number;
            this.balance = 0;
        }

        public void addAmount(int amount) {
            this.balance += amount;
        }

        public long getBalance() {
            return balance;
        }

        public String getNumber() {
            return number;
        }
    }
}
