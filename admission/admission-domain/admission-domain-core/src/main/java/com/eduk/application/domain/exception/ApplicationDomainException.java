package com.eduk.application.domain.exception;

import com.eduk.domain.exception.DomainException;

public class ApplicationDomainException extends DomainException {

    public ApplicationDomainException(String message) {
        super(message);
    }

    public ApplicationDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
