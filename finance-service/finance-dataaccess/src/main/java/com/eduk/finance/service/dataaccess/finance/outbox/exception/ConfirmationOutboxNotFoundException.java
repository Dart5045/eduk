package com.eduk.finance.service.dataaccess.finance.outbox.exception;

public class ConfirmationOutboxNotFoundException extends RuntimeException {

    public ConfirmationOutboxNotFoundException(String message) {
        super(message);
    }
}
