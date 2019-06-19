package com.kb.jess.core.model;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String DEFAULT_PROPERTIES_FILE_NAME = "application.properties";
    public static final String KAKAO_BANK = "카카오뱅크";
    public static final List<String> BANK_NAMES = Arrays.asList(KAKAO_BANK, "우리은행" , "국민은행", "신한은행", "하나은행");
    public static final int MAX_CUSTOMER_COUNT = 50000;
    public static final int LATEST_HISTORY_COUNT = 3;
}
