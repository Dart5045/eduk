package com.eduk.domain.event;

public interface DomainEvent<T> {
    void fire();
}

