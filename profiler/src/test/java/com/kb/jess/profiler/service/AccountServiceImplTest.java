package com.kb.jess.profiler.service;

import com.kb.jess.core.model.*;
import com.kb.jess.core.model.type.TransactionType;
import com.kb.jess.core.support.util.LocalDateTimeUtils;
import com.kb.jess.profiler.TestBase;
import com.kb.jess.profiler.repository.AccountRepository;
import com.kb.jess.profiler.repository.CustomerRepository;
import com.kb.jess.profiler.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AccountServiceImplTest extends TestBase {
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setup() {
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);

        Mockito.when(accountRepository.saveAccount(Mockito.anyLong(), Mockito.any())).thenReturn(getDefaultAccount());
        Mockito.when(accountRepository.findAccountByCustomerNumber(Mockito.anyLong())).thenReturn(Optional.of(getDefaultAccount()));
        Mockito.when(customerRepository.findCustomerNumberByAccountNumber(Mockito.anyString())).thenReturn(1l);
        Mockito.when(customerRepository.saveCustomer(Mockito.anyLong(), Mockito.any())).thenReturn(getDefaultCustomer());
        Mockito.when(customerRepository.findCustomerByCustomerNumber(Mockito.anyLong())).thenReturn(Optional.of(getDefaultCustomer()));

        accountService = new AccountServiceImpl(accountRepository, customerRepository);
    }

    @Test
    @DisplayName("고객번호로 계좌 정보 조회 테스트")
    public void findAccountByCustomerNumberTest() {
        Account account = accountService.findAccountByCustomerNumber(1l).orElseThrow(() -> new NullPointerException("Not found account"));

        Assertions.assertEquals(account.getBalance(), initialAmount);
        Assertions.assertEquals(account.getAccountNumber(), getDefaultAccount().getAccountNumber());
    }

    @Test
    @DisplayName("입금 거래 저장 테스트")
    public void saveTransactionLogTest() {
        final TransactionLog transactionLog = new TransactionLog(TransactionType.DEPOSIT, "1", 200000, 1, "2019-09-17_09:00:00");
        final Account account = saveTransactionLog(transactionLog);

        Assertions.assertFalse(account == null);
        Assertions.assertEquals(account.getBalance(), initialAmount + transactionLog.getAmount());
        Assertions.assertEquals(account.getAggregation().getLatestTransactions().size(), 1);
        Assertions.assertEquals(account.getAggregation().getLatestTransactions().get(0).getAmount(), initialAmount);
        Assertions.assertEquals(account.getAggregation().getMinDepositLog().get().getAmount(), initialAmount);
        Assertions.assertEquals(account.getAggregation().getMaxDepositLog().get().getAmount(), initialAmount);
    }

    @Test
    @DisplayName("출금 거래 저장 테스트")
    void saveTransactionWithdrawlLogTest() {
        TransactionLog transactionLog = new TransactionLog(TransactionType.WITHDRAWL, "1", -100000, 1, "2019-09-17_09:00:00");
        Account account = saveTransactionLog(transactionLog);

        Assertions.assertTrue(account != null);
        Assertions.assertEquals(account.getBalance(), initialAmount + transactionLog.getAmount());
    }

    @Test
    @DisplayName("(타행) 계좌 이체 저장 테스트")
    public void saveTransferLogTest_OtherBanks() {
        long amount = 100000l;
        String sourceAccountNumber = "2345-2345-2345-2345";
        TransferTransactionLog transferLog = new TransferTransactionLog(
                "우리은행",
                sourceAccountNumber,
                getDefaultCustomer().getName(),
                getDefaultAccount().getAccountNumber(),
                amount,
                1l,
                LocalDateTimeUtils.nowString());
        Account account = accountService.saveTransferLog(transferLog).orElseThrow(() -> new NullPointerException("Not found accout"));

        Assertions.assertEquals(account.getBalance() , initialAmount + amount);
        Assertions.assertEquals(account.getAggregation().getLatestTransactions().size(), 1);
        Assertions.assertEquals(account.getAggregation().getLatestTransactions().get(0).getAmount(), amount);
        Assertions.assertEquals(account.getAggregation().getMinTransferLog().get().getAmount(), amount);
        Assertions.assertEquals(account.getAggregation().getMaxTransferLog().get().getAmount(), amount);
        Assertions.assertEquals(((TransferTransactionLog)account.getAggregation().getLatestTransactions().get(0)).getSourceAccountNumber(), sourceAccountNumber);
    }

    //sourceBank의 balance 줄어드는건 integration 테스트에서 확인할 것.
    @Test
    @DisplayName("카카오뱅크끼리 계좌 이체 저장 테스트")
    public void saveTransferLogTest_BetweenSameKaKaoBanks() {
        long amount = 100000l;
        String sourceAccountNumber = "2345-2345-2345-2345";
        TransferTransactionLog transferLog = new TransferTransactionLog(
                Constants.KAKAO_BANK,
                sourceAccountNumber,
                getDefaultCustomer().getName(),
                getDefaultAccount().getAccountNumber(),
                amount,
                1l,
                LocalDateTimeUtils.nowString());
        Account account = accountService.saveTransferLog(transferLog).orElseThrow(() -> new NullPointerException("Not found account"));

        Assertions.assertEquals(account.getBalance() , initialAmount + amount);
        Assertions.assertEquals(account.getAggregation().getMinTransferLog().get().getAmount(), amount);
        Assertions.assertEquals(account.getAggregation().getMaxTransferLog().get().getAmount(), amount);
        Assertions.assertEquals(account.getAggregation().getLatestTransactions().size(), 1);
        Assertions.assertEquals(account.getAggregation().getLatestTransactions().get(0).getTransactionType(), TransactionType.TRANSFER);
        Assertions.assertEquals(((TransferTransactionLog)account.getAggregation().getLatestTransactions().get(0)).getSourceAccountNumber(), sourceAccountNumber);
    }

    private Account saveTransactionLog(TransactionLog transactionLog) {
        return accountService.saveTransactionLog(transactionLog).orElseThrow(() -> new NullPointerException("Not found account"));
    }
}
