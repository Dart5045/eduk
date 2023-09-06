package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.entity.Confirmation;
import com.eduk.domain.event.publisher.DomainEventPublisher;

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
