package com.eduk.admission.service.domain;

import com.eduk.domain.entity.Confirmation;
import com.eduk.domain.event.ConfirmationCreatedEvent;
import com.eduk.domain.event.ConfirmationPaidEvent;
import com.eduk.domain.event.ConfirmationCancelledEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

import java.util.List;

public interface ConfirmationDomainService {

    ConfirmationCreatedEvent validateAndInitiateConfirmation(Confirmation confirmation, DomainEventPublisher<ConfirmationCreatedEvent> confirmationCreatedEventDomainEventPublisher);

    ConfirmationPaidEvent payConfirmation(Confirmation confirmation, DomainEventPublisher<ConfirmationPaidEvent> confirmationPaidEventDomainEventPublisher);

    void approveConfirmation(Confirmation confirmation);

    ConfirmationCancelledEvent cancelConfirmationPayment(Confirmation confirmation, List<String> failureMessages, DomainEventPublisher<ConfirmationCancelledEvent> confirmationCancelledEventDomainEventPublisher);

    void cancelConfirmation(Confirmation confirmation, List<String> failureMessages);
}
