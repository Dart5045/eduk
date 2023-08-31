package com.eduk.service.domain.ports.output.message.publisher.payment;

import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

public interface ConfirmationCreatedPaymentRequestMessagePublisher
extends DomainEventPublisher<ConfirmationCreatedEvent> {
}
