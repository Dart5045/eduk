package com.eduk.admission.service.domain.event;

public interface DomainEvent<T> {
    void fire();
}

