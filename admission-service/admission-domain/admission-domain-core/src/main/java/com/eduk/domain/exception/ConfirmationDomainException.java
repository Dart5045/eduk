package com.eduk.domain.exception;

public class ConfirmationDomainException extends DomainException {

    public ConfirmationDomainException(String message) {
        super(message);
    }

    public ConfirmationDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}