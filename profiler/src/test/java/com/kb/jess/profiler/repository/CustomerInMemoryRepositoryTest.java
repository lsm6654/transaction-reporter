package com.kb.jess.profiler.repository;

import com.kb.jess.core.model.Customer;
import com.kb.jess.profiler.TestBase;
import com.kb.jess.profiler.repository.impl.CustomerInMemoryRepository;
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
class CustomerInMemoryRepositoryTest extends TestBase {
    private CustomerInMemoryRepository repository;

    @BeforeEach
    public void mockSetup() {
        repository = Mockito.mock(CustomerInMemoryRepository.class);
        Mockito.when(repository.findCustomer(1l)).thenReturn(Optional.of(getDefaultCustomer()));
        Mockito.when(repository.saveCustomer(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(repository.saveCustomerNumber(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(repository.findCustomerByCustomerId(Mockito.any())).thenCallRealMethod();
        Mockito.when(repository.findCustomerByCustomerNumber(Mockito.any())).thenCallRealMethod();
        Mockito.when(repository.findCustomerNumberByAccountNumber(Mockito.any())).thenCallRealMethod();
        Mockito.when(repository.findCustomerNumber(Mockito.any())).thenCallRealMethod();
        Mockito.when(repository.saveAccountNumber(Mockito.any(), Mockito.any())).thenCallRealMethod();
    }

    @Test
    @DisplayName("고객 정보 저장 테스트")
    public void saveCustomerTest() {
        Customer customer = repository.saveCustomer(1l, getDefaultCustomer());

        Assertions.assertEquals(customer.getName(), getDefaultCustomer().getName());
        Assertions.assertEquals(customer.getBirthday(), getDefaultCustomer().getBirthday());
    }

    @Test
    @DisplayName("계좌번호와 고객번호 매핑 데이터 저장 테스트")
    public void saveCustomerNumber() {
        Long customerNumber = repository.saveCustomerNumber(1l, getDefaultAccount().getAccountNumber());
        Long savedCustomerNumber = repository.findCustomerNumberByAccountNumber(getDefaultAccount().getAccountNumber());

        Assertions.assertEquals(customerNumber, 1l);
        Assertions.assertEquals(customerNumber, savedCustomerNumber);
    }

    @Test
    @DisplayName("계좌번호로 고객번호 조회 테스트")
    public void findCustomerNumberByAccountNumberTest() {
        Long customerNumber = repository.findCustomerNumberByAccountNumber(getDefaultAccount().getAccountNumber());
        Assertions.assertEquals(customerNumber, 1l);
    }

    @Test
    @DisplayName("고객번호로 고객 정보 조회 테스트")
    public void findCustomerByCustomerNumberTest() {
        Customer customer = repository.findCustomerByCustomerNumber(1l).orElseThrow(() -> new RuntimeException("Not found customer"));

        Assertions.assertEquals(customer.getName(), getDefaultCustomer().getName());
        Assertions.assertEquals(customer.getBirthday(), getDefaultCustomer().getBirthday());
    }
}