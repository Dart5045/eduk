package com.eduk.finance.service.dataaccess.finance.entity;

import com.eduk.domain.valueobject.ConfirmationApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "confirmation_approval", schema = "finance")
@Entity
public class ConfirmationApprovalEntity {

    @Id
    private UUID id;
    private UUID financeId;
    private UUID confirmationId;
    @Enumerated(EnumType.STRING)
    private ConfirmationApprovalStatus status;
}
