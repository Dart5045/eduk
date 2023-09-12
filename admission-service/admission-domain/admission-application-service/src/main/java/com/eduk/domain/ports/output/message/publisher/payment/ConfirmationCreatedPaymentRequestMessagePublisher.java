package com.eduk.domain.ports.output.message.publisher.payment;

import com.eduk.domain.event.publisher.DomainEventPublisher;
import com.eduk.domain.event.ConfirmationCreatedEvent;

public interface ConfirmationCreatedPaymentRequestMessagePublisher
extends DomainEventPublisher<ConfirmationCreatedEvent> {
}