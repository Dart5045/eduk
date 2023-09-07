package com.eduk.admission.service.domain.event.publisher;

import com.eduk.admission.service.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
