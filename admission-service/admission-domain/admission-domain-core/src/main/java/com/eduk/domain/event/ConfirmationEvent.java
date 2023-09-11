package com.eduk.domain.event;

import com.eduk.domain.entity.Confirmation;

import java.time.ZonedDateTime;

public abstract class ConfirmationEvent implements DomainEvent<Confirmation> {
    private final Confirmation confirmation;
    private final ZonedDateTime createdAt;

    public ConfirmationEvent(Confirmation confirmation, ZonedDateTime createdAt) {
        this.confirmation = confirmation;
        this.createdAt = createdAt;
    }

    public Confirmation getConfirmation() {
        return confirmation;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
