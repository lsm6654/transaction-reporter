package com.kb.jess.profiler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.jess.core.exception.TransactionReporterException;
import com.kb.jess.profiler.ProfilerApplication;
import com.kb.jess.profiler.TestBase;
import com.kb.jess.profiler.service.impl.AccountServiceImpl;
import com.kb.jess.profiler.service.impl.CustomerServiceImpl;
import com.kb.jess.profiler.util.HttpTestUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import spark.Spark;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProfilerControllerTest extends TestBase {
    private static CustomerServiceImpl customerService;
    private static AccountServiceImpl accountService;

    @BeforeAll
    public static void setup() {
        customerService = Mockito.mock(CustomerServiceImpl.class);
        accountService = Mockito.mock(AccountServiceImpl.class);

        context.addBean(CustomerServiceImpl.class, customerService);
        context.addBean(AccountServiceImpl.class, accountService);
        context.addBean(ObjectMapper.class, context.objectMapper());

        Mockito.when(customerService.findCustomer(Mockito.anyLong())).thenAnswer( i -> {
            Long argument = (Long) i.getArguments()[0];
            if (argument.equals(1l)) {
                return Optional.of(getDefaultCustomer());
            } else if (argument.equals(2l)) {
                throw new TransactionReporterException("TEST Not found customer");
            } else {
                throw new RuntimeException("TEST runtimeException");
            }
        });
        Mockito.when(accountService.findAccountByCustomerNumber(Mockito.anyLong())).thenReturn(Optional.of(getDefaultAccount()));

        ProfilerApplication.runApiServer();
        Spark.awaitInitialization();
    }

    @AfterAll
    public static void close() {
        Spark.stop();
    }

    @Test
    @DisplayName("고객 정보 조회 테스트")
    public void customerFindTest() {
        HttpTestUtils.TestResponse response = HttpTestUtils.request("GET", "/customers/1", null);

        Assertions.assertEquals(response.status, 200);
    }

    @Test
    @DisplayName("고객 정보 조회 테스트 - 사용자 정의 예외 발생")
    public void customerFindTest_ThrowTransactionException() {
        HttpTestUtils.TestResponse response = HttpTestUtils.request("GET", "/customers/2", null);

        Assertions.assertEquals(response.status, 200);
    }

    @Test
    @DisplayName("고객 정보 조회 테스트 - 예외 발생")
    public void customerFindTest_ThrowException() {
        HttpTestUtils.TestResponse response = HttpTestUtils.request("GET", "/customers/3", null);

        Assertions.assertEquals(response.status, 500);
    }

    @Test
    @DisplayName("계좌 정보 조회 테스트")
    public void accountFindByCustomerNumberTest() {
        HttpTestUtils.TestResponse response = HttpTestUtils.request("GET", "/customers/1/account", null);

        Assertions.assertEquals(response.status, 200);
    }
}
