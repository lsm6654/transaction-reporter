package com.kb.jess.core.exception;

public class BeanDefinitionException extends RuntimeException {

    public BeanDefinitionException(final String message) {
        super(message);
    }

    public BeanDefinitionException(final String message, final Exception e) {
        super(message, e);
    }
}
