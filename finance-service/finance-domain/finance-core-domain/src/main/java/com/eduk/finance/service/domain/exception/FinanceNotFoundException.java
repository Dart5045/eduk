package com.eduk.finance.service.domain.exception;

import com.eduk.admission.service.domain.exception.DomainException;

public class FinanceNotFoundException extends DomainException {
    public FinanceNotFoundException(String message) {
        super(message);
    }

    public FinanceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
