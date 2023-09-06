package com.eduk.application.domain;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.event.ConfirmationCancelledEvent;
import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.application.domain.event.ConfirmationPaidEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

import java.util.List;

public interface ConfirmationDomainService {

    ConfirmationCreatedEvent validateAndInitiateConfirmation(Confirmation confirmation);

    ConfirmationPaidEvent payConfirmation(Confirmation confirmation, DomainEventPublisher<ConfirmationPaidEvent> confirmationPaidEventDomainEventPublisher);

    void approveConfirmation(Confirmation application);
    ConfirmationCancelledEvent cancelFeePaymentEvent(Confirmation confirmation, List<String> failureMessages);
    ConfirmationCancelledEvent cancelConfirmationPayment(Confirmation confirmation, List<String> failureMessages, DomainEventPublisher<ConfirmationCancelledEvent> confirmationCancelledEventDomainEventPublisher);
    void cancelConfirmation(Confirmation confirmation, List<String> failureMessages);

}
