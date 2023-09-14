package com.eduk.admission.service.domain.event;

import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;

public class ConfirmationPaidEvent extends ConfirmationEvent {

    public ConfirmationPaidEvent(Confirmation confirmation,
                                 ZonedDateTime createdAt) {
        super(confirmation, createdAt);
    }
}
