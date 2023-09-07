package com.eduk.admission.service.domain.ports.output.message.publisher.payment;

import com.eduk.admission.service.domain.event.publisher.DomainEventPublisher;
import com.eduk.admission.service.domain.event.ConfirmationCreatedEvent;

public interface ConfirmationCreatedPaymentRequestMessagePublisher
extends DomainEventPublisher<ConfirmationCreatedEvent> {
}