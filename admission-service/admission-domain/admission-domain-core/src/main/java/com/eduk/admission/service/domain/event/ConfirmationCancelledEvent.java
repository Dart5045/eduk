package com.eduk.admission.service.domain.event;

import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.event.publisher.DomainEventPublisher;

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
