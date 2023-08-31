package com.eduk.application.domain;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.event.ConfirmationCancelledEvent;
import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.application.domain.event.ConfirmationPaidEvent;
import com.eduk.application.domain.exception.ApplicationDomainException;
import com.eduk.application.domain.valueobject.ApplicationStatus;
import com.eduk.domain.valueobject.Money;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class ConfirmationDomainServiceImpl implements ConfirmationDomainService {
    @Override
    public ConfirmationCreatedEvent validateAndInitiatePaymentFee(Confirmation confirmation) {
        validateApplication(confirmation);
        updateConfirmationPrice(confirmation);
        confirmation.validateFeePayment();
        confirmation.initFeePayment();
        log.info("PaymentFee with id:{} is initiated");
        return new ConfirmationCreatedEvent(confirmation, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    private void updateConfirmationPrice(Confirmation confirmation) {
        confirmation.setAmount(new Money(new BigDecimal(111)));
    }

    private void validateApplication(Confirmation confirmation) {
        if(confirmation.getStatus()== ApplicationStatus.CANCELLED){
            throw new ApplicationDomainException("Application with id:"+confirmation.getId()+ " is not current active");
        }
    }

    @Override
    public ConfirmationPaidEvent payFee(Confirmation application) {

        application.payConfirmation();
        log.info("Application Payment fee with id : {} is paid",application.getId());
        return new ConfirmationPaidEvent(application,ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public void approvePaymentFee(Confirmation application) {
        log.info("Application Payment fee with id : {} is approved",application.getId());
    }

    @Override
    public ConfirmationCancelledEvent cancelFeePaymentEvent(Confirmation application, List<String> failureMessages) {
        application.initCancellingConfirmation(failureMessages);
        log.info("Payment fee is cancelling for application id : {}",application.getId());
        return new ConfirmationCancelledEvent(application,ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public void cancelPaymentFee(Confirmation application, List<String> failureMessages) {
        application.cancelConfirmation(failureMessages);
        log.info("Payment fee for application id : {} is cancelled",application.getId());
    }
}
