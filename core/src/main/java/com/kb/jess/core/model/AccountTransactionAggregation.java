package com.kb.jess.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountTransactionAggregation {
    private final List<TransactionLog> latestTransactions;
    private final TransactionLog minDepositLog;
    private final TransactionLog maxDepositLog;
    private final TransactionLog minWithdrawlLog;
    private final TransactionLog maxWithdrawlLog;
    private final TransferTransactionLog minTransferLog;
    private final TransferTransactionLog maxTransferLog;

    public AccountTransactionAggregation(final List<TransactionLog> latestTransactions,
                                         final TransactionLog minDepositLog,
                                         final TransactionLog maxDepositLog,
                                         final TransactionLog minWithdrawlLog,
                                         final TransactionLog maxWithdrawlLog,
                                         final TransferTransactionLog minTransferLog,
                                         final TransferTransactionLog maxTransferLog) {
        this.latestTransactions = latestTransactions;
        this.minDepositLog = minDepositLog;
        this.maxDepositLog = maxDepositLog;
        this.minWithdrawlLog = minWithdrawlLog;
        this.maxWithdrawlLog = maxWithdrawlLog;
        this.minTransferLog = minTransferLog;
        this.maxTransferLog = maxTransferLog;
    }

    public static AccountTransactionAggregation empty() {
        return new AccountTransactionAggregation(new ArrayList<>(), null, null, null, null, null, null);
    }

    //새로운 거래에 대한 Aggregation
    public AccountTransactionAggregation newAggregation(final TransactionLog transactionLog) {
        latestTransactions.add(0, transactionLog);
        final List<TransactionLog> latestTransactionLogs = latestTransactions.stream().limit(Constants.LATEST_HISTORY_COUNT).collect(Collectors.toList());

        switch (transactionLog.getTransactionType()) {
            case DEPOSIT:
                final TransactionLog minDeposit = getMinDepositLog().map(i -> getMinimumTransactionAmount(i, transactionLog)).orElse(transactionLog);
                final TransactionLog maxDeposit = getMaxDepositLog().map(i -> getMaximumTransactionAmount(i, transactionLog)).orElse(transactionLog);

                return new AccountTransactionAggregation(latestTransactionLogs, minDeposit, maxDeposit,
                        this.minWithdrawlLog, this.maxWithdrawlLog, this.minTransferLog, this.maxTransferLog);

            case WITHDRAWL:
                final TransactionLog minWithdrawl = getMinWithdrawlLog().map(i -> getMinimumTransactionAmount(i, transactionLog)).orElse(transactionLog);
                final TransactionLog maxWithdrawl = getMaxWithdrawlLog().map(i -> getMaximumTransactionAmount(i, transactionLog)).orElse(transactionLog);

                return new AccountTransactionAggregation(latestTransactionLogs, this.minDepositLog, this.maxDepositLog,
                        minWithdrawl, maxWithdrawl, this.minTransferLog, this.maxTransferLog);

            case TRANSFER:
                final TransactionLog minTransfer = getMinTransferLog().map(i -> getMinimumTransactionAmount(i, transactionLog)).orElse(transactionLog);
                final TransactionLog maxTransfer = getMaxTransferLog().map(i -> getMaximumTransactionAmount(i, transactionLog)).orElse(transactionLog);

                return new AccountTransactionAggregation(latestTransactionLogs, this.minDepositLog, this.maxDepositLog,
                        this.minWithdrawlLog, this.maxWithdrawlLog, (TransferTransactionLog) minTransfer, (TransferTransactionLog) maxTransfer);

            default:
                throw new IllegalArgumentException("Not supported transaction type");
        }
    }

    public TransactionLog getMinimumTransactionAmount(TransactionLog minTransactionLog, TransactionLog transactionLog) {
        if (minTransactionLog.getAmount() > Math.abs(transactionLog.getAmount())) {
            return transactionLog;
        }
        return minTransactionLog;
    }

    public TransactionLog getMaximumTransactionAmount(TransactionLog maxTransactionLog, TransactionLog transactionLog) {
        if (maxTransactionLog.getAmount() < Math.abs(transactionLog.getAmount())) {
            return transactionLog;
        }
        return maxTransactionLog;
    }

    public List<TransactionLog> getLatestTransactions() {
        return latestTransactions;
    }

    public Optional<TransactionLog> getMinDepositLog() {
        return Optional.ofNullable(minDepositLog);
    }

    public Optional<TransactionLog> getMaxDepositLog() {
        return Optional.ofNullable(maxDepositLog);
    }

    public Optional<TransactionLog> getMinWithdrawlLog() {
        return Optional.ofNullable(minWithdrawlLog);
    }

    public Optional<TransactionLog> getMaxWithdrawlLog() {
        return Optional.ofNullable(maxWithdrawlLog);
    }

    public Optional<TransferTransactionLog> getMinTransferLog() {
        return Optional.ofNullable(minTransferLog);
    }

    public Optional<TransferTransactionLog> getMaxTransferLog() {
        return Optional.ofNullable(maxTransferLog);
    }
}
