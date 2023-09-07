package com.eduk.paymenet.service.domain.ports.output.message.publisher;

import com.eduk.admission.service.domain.event.publisher.DomainEventPublisher;
import com.eduk.payment.service.domain.event.PaymentCompletedEvent;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {
}
