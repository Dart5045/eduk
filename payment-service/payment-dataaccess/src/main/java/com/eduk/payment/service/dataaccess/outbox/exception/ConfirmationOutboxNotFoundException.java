package com.eduk.payment.service.dataaccess.outbox.exception;

public class ConfirmationOutboxNotFoundException extends RuntimeException {

    public ConfirmationOutboxNotFoundException(String message) {
        super(message);
    }
}
