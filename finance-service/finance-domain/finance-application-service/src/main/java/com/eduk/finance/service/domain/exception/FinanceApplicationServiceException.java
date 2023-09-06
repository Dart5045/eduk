package com.eduk.finance.service.domain.exception;


import com.eduk.domain.exception.DomainException;

public class FinanceApplicationServiceException extends DomainException {
    public FinanceApplicationServiceException(String message) {
        super(message);
    }

    public FinanceApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
