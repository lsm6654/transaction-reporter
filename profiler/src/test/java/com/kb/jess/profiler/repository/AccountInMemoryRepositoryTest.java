package com.kb.jess.profiler.repository;

import com.kb.jess.core.model.Account;
import com.kb.jess.profiler.TestBase;
import com.kb.jess.profiler.repository.impl.AccountInMemoryRepository;
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
public class AccountInMemoryRepositoryTest extends TestBase {
    private AccountInMemoryRepository repository;

    @BeforeEach
    public void mockSetup() {
        repository = Mockito.mock(AccountInMemoryRepository.class);
        Mockito.when(repository.findCustomer(1l)).thenReturn(Optional.of(getDefaultCustomer()));
        Mockito.when(repository.findCustomerByCustomerId(1l)).thenReturn(getDefaultCustomer());
        Mockito.when(repository.saveAccount(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(repository.saveCustomer(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(repository.findAccountByCustomerNumber(Mockito.any())).thenCallRealMethod();
    }

    @Test
    @DisplayName("계좌 정보 저장 테스트")
    public void saveAccountTest() {
        Account account = repository.saveAccount(1l, getDefaultAccount());

        Assertions.assertEquals(account.getBalance(), initialAmount);
        Assertions.assertEquals(account.getAccountNumber(), getDefaultAccount().getAccountNumber());
    }

    @Test
    @DisplayName("고객번호로 계좌정보 조회 테스트")
    public void findAccountByCustomerNumberTest() {
        Account account = repository.findAccountByCustomerNumber(1l).orElseThrow(() ->  new NullPointerException("Not found account"));

        Assertions.assertEquals(account.getBalance(), initialAmount);
        Assertions.assertEquals(account.getAccountNumber(), getDefaultAccount().getAccountNumber());
    }
}
