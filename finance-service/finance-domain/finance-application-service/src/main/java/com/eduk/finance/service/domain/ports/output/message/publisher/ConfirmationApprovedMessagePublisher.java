package com.eduk.finance.service.domain.ports.output.message.publisher;


import com.eduk.domain.event.publisher.DomainEventPublisher;
import com.eduk.finance.service.domain.event.ConfirmationApprovedEvent;

public interface ConfirmationApprovedMessagePublisher extends DomainEventPublisher<ConfirmationApprovedEvent> {
}
