package com.kb.jess.generator;

import com.kb.jess.generator.config.GeneratorContext;
import com.kb.jess.generator.service.GeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorApplication {
    private static final Logger log = LoggerFactory.getLogger(GeneratorApplication.class);
    private static final GeneratorContext context = new GeneratorContext();

    public static void main(String[] args) throws InterruptedException {
        context.initialize();
        final GeneratorService generatorService = GeneratorContext.getBean(GeneratorService.class);
        registerShutdownHook(generatorService);

        while(true) {
            generatorService.run();
            generatorService.await();
        }
    }

    private static void registerShutdownHook(final GeneratorService generatorService) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("SIGTERM hook");
            generatorService.close();
            log.info("server stopped");
        }));
    }
}
