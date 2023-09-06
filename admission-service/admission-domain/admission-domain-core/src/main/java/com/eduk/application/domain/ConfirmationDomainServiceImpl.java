package com.eduk.application.domain;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.event.ConfirmationCancelledEvent;
import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.application.domain.event.ConfirmationPaidEvent;
import com.eduk.application.domain.exception.ConfirmationDomainException;
import com.eduk.application.domain.valueobject.ConfirmationStatus;
import com.eduk.domain.event.publisher.DomainEventPublisher;
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
    public ConfirmationCreatedEvent validateAndInitiateConfirmation(Confirmation confirmation,
                                                      DomainEventPublisher<ConfirmationCreatedEvent>
                                                              orderCreatedEventDomainEventPublisher) {
        //validateFinance(finance);
        //setConfirmationProductInformation(order, restaurant);
        confirmation.validateConfirmation();
        confirmation.initializeConfirmation();
        log.info("Order with id: {} is initiated", confirmation.getId().getValue());
        return new ConfirmationCreatedEvent(confirmation
                , ZonedDateTime.now(ZoneId.of(UTC))
                , orderCreatedEventDomainEventPublisher);
    }


    @Override
    public ConfirmationPaidEvent payConfirmation(Confirmation confirmation, DomainEventPublisher<ConfirmationPaidEvent> confirmationPaidEventDomainEventPublisher) {
        confirmation.payConfirmation();
        log.info("Application Payment fee with id : {} is paid",confirmation.getId());
        return new ConfirmationPaidEvent(confirmation, ZonedDateTime.now(ZoneId.of(UTC)), confirmationPaidEventDomainEventPublisher);
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
    public void approveConfirmation(Confirmation application) {
        log.info("Application Payment fee with id : {} is approved",application.getId());
    }


    @Override
    public ConfirmationCancelledEvent cancelConfirmationPayment(Confirmation confirmation, List<String> failureMessages, DomainEventPublisher<ConfirmationCancelledEvent> confirmationCancelledEventDomainEventPublisher) {
            confirmation.initCancellingConfirmation(failureMessages);
            log.info("Order payment is cancelling for order id: {}", confirmation.getId().getValue());
            return new ConfirmationCancelledEvent(confirmation, ZonedDateTime.now(ZoneId.of(UTC)),
                    confirmationCancelledEventDomainEventPublisher);
    }



    @Override
    public void cancelConfirmation(Confirmation confirmation, List<String> failureMessages) {
        confirmation.cancelConfirmation(failureMessages);
        log.info("Payment fee for application id : {} is cancelled",confirmation.getId());
    }
}
