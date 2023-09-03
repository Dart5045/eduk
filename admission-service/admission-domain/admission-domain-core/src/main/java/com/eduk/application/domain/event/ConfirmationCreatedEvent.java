package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.entity.Confirmation;

import java.time.ZonedDateTime;

public class ConfirmationCreatedEvent extends ConfirmationEvent {

    public ConfirmationCreatedEvent(Confirmation confirmation, ZonedDateTime createdAt) {
        super(confirmation, createdAt);
    }
}
