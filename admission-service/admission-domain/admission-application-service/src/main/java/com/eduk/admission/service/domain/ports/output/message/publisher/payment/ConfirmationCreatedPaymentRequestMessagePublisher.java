package com.eduk.admission.service.domain.ports.output.message.publisher.payment;

import com.eduk.admission.service.domain.event.ConfirmationCreatedEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

public interface ConfirmationCreatedPaymentRequestMessagePublisher
extends DomainEventPublisher<ConfirmationCreatedEvent> {
}