package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Application;
import com.eduk.domain.event.DomainEvent;

import java.time.ZonedDateTime;

public abstract class ConfirmationEvent implements DomainEvent<Application> {
    private final Application application;
    private final ZonedDateTime createdAt;

    public ConfirmationEvent(Application application, ZonedDateTime createdAt) {
        this.application = application;
        this.createdAt = createdAt;
    }

    public Application getApplication() {
        return application;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
