package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.entity.Confirmation;
import com.eduk.domain.event.DomainEvent;

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
