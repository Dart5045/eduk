package com.eduk.application.domain;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.event.ConfirmationCancelledEvent;
import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.application.domain.event.ConfirmationPaidEvent;
import com.eduk.application.domain.exception.ConfirmationDomainException;
import com.eduk.application.domain.valueobject.ApplicationStatus;
import com.eduk.application.domain.valueobject.ConfirmationStatus;
import com.eduk.domain.valueobject.Money;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.eduk.domain.DomainConstants.UTC;

@Slf4j
public class ConfirmationDomainServiceImpl implements ConfirmationDomainService {
    @Override
    public ConfirmationCreatedEvent validateAndInitiatePaymentFee(Confirmation confirmation) {
        validateApplication(confirmation);
        updateConfirmationPrice(confirmation);
        confirmation.validateConfirmation();
        confirmation.initializeConfirmation();
        log.info("PaymentFee with id:{} is initiated");
        return new ConfirmationCreatedEvent(confirmation, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    private void updateConfirmationPrice(Confirmation confirmation) {
        confirmation.setAmount(new Money(new BigDecimal(111)));
    }

    private void validateApplication(Confirmation confirmation) {
        if(confirmation.getConfirmationStatus()== ConfirmationStatus.CANCELLED){
            throw new ConfirmationDomainException("Application with id:"+confirmation.getId()+ " is not current active");
        }
    }

    @Override
    public ConfirmationPaidEvent payFee(Confirmation confirmation) {

        confirmation.payConfirmation();
        log.info("Application Payment fee with id : {} is paid",confirmation.getId());
        return new ConfirmationPaidEvent(confirmation,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveConfirmation(Confirmation application) {
        log.info("Application Payment fee with id : {} is approved",application.getId());
    }

    @Override
    public ConfirmationCancelledEvent cancelFeePaymentEvent(Confirmation confirmation, List<String> failureMessages) {
        confirmation.initCancellingConfirmation(failureMessages);
        log.info("Payment fee is cancelling for application id : {}",confirmation.getId());
        return new ConfirmationCancelledEvent(confirmation,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelPaymentFee(Confirmation confirmation, List<String> failureMessages) {
        confirmation.cancelConfirmation(failureMessages);
        log.info("Payment fee for application id : {} is cancelled",confirmation.getId());
    }
}
