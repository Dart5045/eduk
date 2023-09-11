package com.eduk.finance.service.domain.ports.output.message.publisher;


import com.eduk.finance.service.domain.event.ConfirmationRejectedEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

public interface ConfirmationRejectedMessagePublisher extends DomainEventPublisher<ConfirmationRejectedEvent> {
}
