package com.eduk.finance.service.domain.ports.output.message.publisher;


import com.eduk.finance.service.domain.event.ConfirmationApprovedEvent;
import com.eduk.domain.event.publisher.DomainEventPublisher;

public interface ConfirmationApprovedMessagePublisher extends DomainEventPublisher<ConfirmationApprovedEvent> {
}
