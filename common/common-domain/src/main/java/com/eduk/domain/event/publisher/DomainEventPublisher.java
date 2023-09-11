package com.eduk.domain.event.publisher;

import com.eduk.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
