package com.eduk.admission.service.application.exception.handler;

import com.eduk.admission.service.domain.exception.ConfirmationDomainException;
import com.eduk.application.handler.ErrorDTO;
import com.eduk.application.handler.GlobalExceptionHandler;
import com.eduk.admission.service.domain.exception.ConfirmationNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ConfirmationGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {ConfirmationDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ConfirmationDomainException confirmationDomainException) {
        log.error(confirmationDomainException.getMessage(), confirmationDomainException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(confirmationDomainException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {ConfirmationNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(ConfirmationNotFoundException confirmationNotFoundException) {
        log.error(confirmationNotFoundException.getMessage(), confirmationNotFoundException);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(confirmationNotFoundException.getMessage())
                .build();
    }
}
