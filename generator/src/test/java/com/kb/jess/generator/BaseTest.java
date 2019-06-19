package com.kb.jess.generator;

import com.kb.jess.generator.config.GeneratorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BaseTest {
    protected static GeneratorContext context;

    @BeforeAll
    public static void initialize() {
        context = new GeneratorContext();
    }

    public static void initializeAllContextBean() {
        context.initialize();
    }
}
