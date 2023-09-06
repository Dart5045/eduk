package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;


public class ConfirmationCreatedEvent extends ConfirmationEvent {

    private final DomainEventPublisher<ConfirmationCreatedEvent> confirmationCreatedEventDomainEventPublisher;

    public ConfirmationCreatedEvent(Confirmation confirmation,
                                    ZonedDateTime createdAt,
                                    DomainEventPublisher<ConfirmationCreatedEvent> orderCreatedEventDomainEventPublisher) {
        super(confirmation, createdAt);
        this.confirmationCreatedEventDomainEventPublisher = orderCreatedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        confirmationCreatedEventDomainEventPublisher.publish(this);
    }
}


