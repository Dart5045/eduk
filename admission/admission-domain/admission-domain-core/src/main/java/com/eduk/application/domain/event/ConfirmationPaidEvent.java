package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Application;

import java.time.ZonedDateTime;

public class ConfirmationPaidEvent extends ConfirmationEvent {

    public ConfirmationPaidEvent(Application application, ZonedDateTime createdAt) {
        super(application, createdAt);
    }
}
