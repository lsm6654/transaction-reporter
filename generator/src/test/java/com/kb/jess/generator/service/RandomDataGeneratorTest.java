package com.kb.jess.generator.service;

import com.kb.jess.generator.BaseTest;
import org.junit.jupiter.api.Test;

public class RandomDataGeneratorTest extends BaseTest {

    RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

    @Test
    public void generateTest() {
        randomDataGenerator.generate(100);
    }
}
