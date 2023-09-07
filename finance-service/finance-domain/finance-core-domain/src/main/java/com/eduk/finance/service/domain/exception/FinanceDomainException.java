package com.eduk.finance.service.domain.exception;

import com.eduk.admission.service.domain.exception.DomainException;

public class FinanceDomainException extends DomainException {
    public FinanceDomainException(String message) {
        super(message);
    }

    public FinanceDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
