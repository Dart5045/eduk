package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Application;

import java.time.ZonedDateTime;

public class ConfirmationCancelledEvent extends ConfirmationEvent {

    public ConfirmationCancelledEvent(Application application, ZonedDateTime createdAt) {
        super(application, createdAt);
    }
}
