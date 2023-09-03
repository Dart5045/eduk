package com.eduk.application.domain.event;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.entity.Confirmation;

import java.time.ZonedDateTime;

public class ConfirmationPaidEvent extends ConfirmationEvent {

    public ConfirmationPaidEvent(Confirmation confirmation, ZonedDateTime createdAt) {
        super(confirmation, createdAt);
    }
}
