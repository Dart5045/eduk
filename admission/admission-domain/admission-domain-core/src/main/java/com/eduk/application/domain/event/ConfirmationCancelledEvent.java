package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.entity.Confirmation;

import java.time.ZonedDateTime;

public class ConfirmationCancelledEvent extends ConfirmationEvent {

    public ConfirmationCancelledEvent(Confirmation confirmation, ZonedDateTime createdAt) {
        super(confirmation, createdAt);
    }
}
