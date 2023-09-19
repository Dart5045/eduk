package com.eduk.finance.service.domain.event;

import com.eduk.finance.service.domain.entity.ConfirmationApproval;
import com.eduk.domain.event.publisher.DomainEventPublisher;
import com.eduk.domain.valueobject.FinanceId;

import java.time.ZonedDateTime;
import java.util.List;

public class ConfirmationRejectedEvent extends ConfirmationApprovalEvent {


    public ConfirmationRejectedEvent(ConfirmationApproval confirmationApproval,
                              FinanceId financeId,
                              List<String> failureMessages,
                              ZonedDateTime createdAt) {
        super(confirmationApproval, financeId, failureMessages, createdAt);
    }

}
