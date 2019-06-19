package com.kb.jess.core.exception;

public class TransactionReporterException extends RuntimeException {

    public TransactionReporterException(final String message) {
        super(message);
    }

    public TransactionReporterException(final String message, final Exception e) {
        super(message, e);
    }
}
