package com.eduk.admission.service.dataaccess.outbox.financeapproval.entity;

import com.eduk.domain.valueobject.ConfirmationStatus;
import com.eduk.outbox.OutboxStatus;
import com.eduk.saga.SagaStatus;
import lombok.*;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "finance_approval_outbox")
@Entity
public class ApprovalOutboxEntity {

    @Id
    private UUID id;
    private UUID sagaId;
    private ZonedDateTime createdAt;
    private ZonedDateTime processedAt;
    private String type;
    private String payload;
    @Enumerated(EnumType.STRING)
    private SagaStatus sagaStatus;
    @Enumerated(EnumType.STRING)
    private ConfirmationStatus confirmationStatus;
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;
    @Version
    private int version;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApprovalOutboxEntity that = (ApprovalOutboxEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

