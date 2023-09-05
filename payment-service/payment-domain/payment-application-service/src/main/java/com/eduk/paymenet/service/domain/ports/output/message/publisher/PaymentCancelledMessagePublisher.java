package com.eduk.paymenet.service.domain.ports.output.message.publisher;

import com.eduk.domain.event.publisher.DomainEventPublisher;
import com.eduk.payment.service.domain.event.PaymentCancelledEvent;
import com.eduk.payment.service.domain.event.PaymentCompletedEvent;

public interface PaymentCancelledMessagePublisher extends DomainEventPublisher<PaymentCancelledEvent> {
}
