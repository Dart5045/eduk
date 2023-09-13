package com.eduk.finance.service.domain.event;

import com.eduk.domain.event.DomainEvent;
import com.eduk.finance.service.domain.entity.ConfirmationApproval;
import com.eduk.domain.valueobject.FinanceId;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class ConfirmationApprovalEvent implements DomainEvent<ConfirmationApproval> {
    private final ConfirmationApproval confirmationApproval;
    private final FinanceId financeId;
    private final List<String> failureMessages;
    private final ZonedDateTime createdAt;

    public ConfirmationApprovalEvent(ConfirmationApproval confirmationApproval,
                                     FinanceId financeId,
                                     List<String> failureMessages,
                                     ZonedDateTime createdAt) {
        this.confirmationApproval = confirmationApproval;
        this.financeId = financeId;
        this.failureMessages = failureMessages;
        this.createdAt = createdAt;
    }

    public ConfirmationApproval getConfirmationApproval() {
        return confirmationApproval;
    }

    public FinanceId getFinanceId() {
        return financeId;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
