package com.eduk.admission.service.domain.exception;

public class ConfirmationNotFoundException extends DomainException {

    public ConfirmationNotFoundException(String message) {
        super(message);
    }

    public ConfirmationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
