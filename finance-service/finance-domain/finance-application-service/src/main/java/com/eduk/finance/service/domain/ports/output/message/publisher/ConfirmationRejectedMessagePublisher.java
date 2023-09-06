package com.eduk.finance.service.domain.ports.output.message.publisher;


import com.eduk.domain.event.publisher.DomainEventPublisher;
import com.eduk.finance.service.domain.event.ConfirmationRejectedEvent;

public interface ConfirmationRejectedMessagePublisher extends DomainEventPublisher<ConfirmationRejectedEvent> {
}
