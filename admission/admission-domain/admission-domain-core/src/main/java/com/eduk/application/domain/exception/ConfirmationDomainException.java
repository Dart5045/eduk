package com.eduk.application.domain.exception;

import com.eduk.domain.exception.DomainException;

public class ConfirmationDomainException extends DomainException {

    public ConfirmationDomainException(String message) {
        super(message);
    }

    public ConfirmationDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
