package com.eduk.admission.service.domain.ports.output.message.publisher.payment;

import com.eduk.domain.event.ConfirmationCancelledEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

public interface ConfirmationCancelledPaymentRequestMessagePublisher
extends DomainEventPublisher<ConfirmationCancelledEvent> {
}
