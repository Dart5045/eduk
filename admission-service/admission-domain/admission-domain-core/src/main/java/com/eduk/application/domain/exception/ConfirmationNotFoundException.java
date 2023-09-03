package com.eduk.application.domain.exception;

import com.eduk.domain.exception.DomainException;

public class ConfirmationNotFoundException extends DomainException {

    public ConfirmationNotFoundException(String message) {
        super(message);
    }

    public ConfirmationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
