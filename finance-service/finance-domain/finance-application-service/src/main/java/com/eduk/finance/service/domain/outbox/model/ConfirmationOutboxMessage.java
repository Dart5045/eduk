package com.eduk.finance.service.domain.outbox.model;

import com.eduk.domain.valueobject.ConfirmationApprovalStatus;
import com.eduk.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ConfirmationOutboxMessage {
    private UUID id;
    private UUID sagaId;
    private ZonedDateTime createdAt;
    private ZonedDateTime processedAt;
    private String type;
    private String payload;
    private OutboxStatus outboxStatus;
    private ConfirmationApprovalStatus approvalStatus;
    private int version;

    public void setOutboxStatus(OutboxStatus status) {
        this.outboxStatus = status;
    }
}
