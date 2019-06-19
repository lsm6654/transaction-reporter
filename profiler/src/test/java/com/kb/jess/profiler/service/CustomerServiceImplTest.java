package com.kb.jess.profiler.service;

import com.kb.jess.core.model.CreateAccountLog;
import com.kb.jess.core.model.Customer;
import com.kb.jess.core.model.RegisterLog;
import com.kb.jess.core.model.SessionStartLog;
import com.kb.jess.profiler.TestBase;
import com.kb.jess.profiler.repository.CustomerRepository;
import com.kb.jess.profiler.service.impl.CustomerServiceImpl;
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
public class CustomerServiceImplTest extends TestBase {
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setup() {
        CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
        Mockito.when(customerRepository.findCustomerNumberByAccountNumber(Mockito.anyString())).thenReturn(1l);
        Mockito.when(customerRepository.saveCustomer(Mockito.anyLong(), Mockito.any())).thenReturn(getDefaultCustomer());
        Mockito.when(customerRepository.findCustomerByCustomerNumber(Mockito.anyLong())).thenReturn(Optional.of(getDefaultCustomer()));

        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    @DisplayName("고객 세션 정보 저장 (고객 정보 존재하지 않을 떄) 테스트")
    void saveSessionLog_EmptyTest() {
        SessionStartLog sessionStartLog = new SessionStartLog(-1l, "2019-06-17_00:00:00");
        Optional<Customer> customer = customerService.saveSessionLog(sessionStartLog);

        Assertions.assertTrue(!customer.isPresent());
    }

    @Test
    @DisplayName("고객 세션 정보 저장 (고객 정보 존재할 때) 테스트")
    void saveSessionLog_IntegrationTest() {
        //register customer & create account
        saveRegisterLogTest();
        saveCreateAccountLogTest();

        SessionStartLog sessionStartLog = new SessionStartLog(1l, "2019-06-17_00:00:00");
        Customer customer = customerService.saveSessionLog(sessionStartLog).orElseThrow(() -> new NullPointerException("Customer NPE"));

        Assertions.assertTrue(customer != null);
        Assertions.assertEquals(customer.getSessionCount().get(), 2);
    }

    @Test
    @DisplayName("가입 정보 저장 테스트")
    void saveRegisterLogTest() {
        RegisterLog registerLog = new RegisterLog(1l, "TEST_NAME", "1988-07-08", "2019-06-17_00:00:00");
        Customer customer = customerService.saveRegisterLog(registerLog).orElseThrow(() -> new NullPointerException("Customer NPE"));

        Assertions.assertEquals(customer.getBirthday(), registerLog.getBirthday());
        Assertions.assertEquals(customer.getRegisterTime(), registerLog.getTransactionTime());
    }

    @Test
    @DisplayName("계좌 생성 저장 테스트")
    void saveCreateAccountLogTest() {
        CreateAccountLog createAccountLog = new CreateAccountLog("1234-1234-1234-1234", 1l, "2019-06-17_00:00:00");
        Customer customer = customerService.saveCreateAccountLog(createAccountLog).orElseThrow(() -> new NullPointerException("Customer NPE"));

        Assertions.assertEquals(customer.getAccount().getAccountNumber(), createAccountLog.getAccountNumber());
        Assertions.assertEquals(customer.getAccount().getBalance(), 0l);
    }

    @Test
    @DisplayName("고객 정보 조회")
    void findOneByCustomerNumberTest() {
        Optional<Customer> customer = customerService.findCustomer(1l);

        Assertions.assertTrue(customer.isPresent());
        Assertions.assertEquals(customer.get().getName(), getDefaultCustomer().getName());
    }
}
