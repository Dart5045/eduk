package com.eduk.finance.service.domain.event;

import com.eduk.finance.service.domain.entity.ConfirmationApproval;
import com.eduk.domain.event.publisher.DomainEventPublisher;
import com.eduk.domain.valueobject.FinanceId;

import java.time.ZonedDateTime;
import java.util.List;

public class ConfirmationApprovedEvent extends ConfirmationApprovalEvent {

    private final DomainEventPublisher<ConfirmationApprovedEvent> confirmationApprovedEventDomainEventPublisher;

    public ConfirmationApprovedEvent(ConfirmationApproval confirmationApproval,
                                     FinanceId financeId,
                                     List<String> failureMessages,
                                     ZonedDateTime createdAt,
                                     DomainEventPublisher<ConfirmationApprovedEvent> confirmationApprovedEventDomainEventPublisher) {
        super(confirmationApproval, financeId, failureMessages, createdAt);
        this.confirmationApprovedEventDomainEventPublisher = confirmationApprovedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        confirmationApprovedEventDomainEventPublisher.publish(this);
    }
}
