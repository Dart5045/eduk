package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;



public class ConfirmationCancelledEvent extends ConfirmationEvent {
    private final DomainEventPublisher<ConfirmationCancelledEvent> confirmationCancelledEventDomainEventPublisher;


    public ConfirmationCancelledEvent(Confirmation confirmation,
                               ZonedDateTime createdAt,
                               DomainEventPublisher<ConfirmationCancelledEvent> confirmationCancelledEventDomainEventPublisher) {
        super(confirmation, createdAt);
        this.confirmationCancelledEventDomainEventPublisher = confirmationCancelledEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        confirmationCancelledEventDomainEventPublisher.publish(this);
    }
}
