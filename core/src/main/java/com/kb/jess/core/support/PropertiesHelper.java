package com.kb.jess.core.support;

import com.kb.jess.core.exception.BeanDefinitionException;
import com.kb.jess.core.model.Constants;
import com.kb.jess.core.support.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
    public static final Properties appProps = new Properties();

    static {
        try {
            initialize();
        } catch (IOException e) {
            throw new BeanDefinitionException("error occurred when properties are initialized.", e);
        }
    }

    public static void initialize() throws IOException {
        initialize(Constants.DEFAULT_PROPERTIES_FILE_NAME);
    }

    public static void initialize(final String file) throws IOException {
        final String propertyPath = PropertiesHelper.class.getClassLoader().getResource(file).getPath();
        appProps.load(new FileInputStream(propertyPath));
    }

    public static String getProperty(final String key) {
        return appProps.getProperty(key);
    }

    public static String getProperty(final String key, final String defaultValue) {
        final String property = getProperty(key);
        if (StringUtils.isEmpty(property)) {
            return defaultValue;
        }

        return property;
    }
}
