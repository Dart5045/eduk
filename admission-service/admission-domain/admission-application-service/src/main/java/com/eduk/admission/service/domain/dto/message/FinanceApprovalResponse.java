package com.eduk.admission.service.domain.dto.message;

import com.eduk.domain.valueobject.ConfirmationApprovalStatus;
import com.eduk.domain.valueobject.FinanceApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class FinanceApprovalResponse {
    private String id;
    private String sagaId;
    private String confirmationId;
    private String financeId;
    private Instant createdAt;
    private ConfirmationApprovalStatus confirmationApprovalStatus;
    private List<String> failureMessages;
}
