package com.kb.jess.core.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PropertiesHelperTest {

    @Test
    public void getPropertyTest() {
        String version = PropertiesHelper.getProperty("version");
        Assertions.assertNotNull(version);
    }
}
