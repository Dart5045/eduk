package com.eduk.admission.service.domain.event;

import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;

public class ConfirmationPaidEvent extends ConfirmationEvent {

    private final DomainEventPublisher<ConfirmationPaidEvent> confirmationPaidEventDomainEventPublisher;


    public ConfirmationPaidEvent(Confirmation confirmation,
                                 ZonedDateTime createdAt,
                                 DomainEventPublisher<ConfirmationPaidEvent> confirmationPaidEventDomainEventPublisher) {
        super(confirmation, createdAt);
        this.confirmationPaidEventDomainEventPublisher = confirmationPaidEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        confirmationPaidEventDomainEventPublisher.publish(this);
    }

}
