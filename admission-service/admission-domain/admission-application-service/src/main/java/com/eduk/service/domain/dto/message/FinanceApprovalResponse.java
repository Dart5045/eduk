package com.eduk.service.domain.dto.message;

import com.eduk.domain.valueobject.FinanceApprovalStatus;
import com.eduk.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class FinanceApprovalResponse {
    private String id;
    private String sagaId;
    private String applicationId;
    private String confirmationId;
    private String paymentId;
    private Instant createdAt;
    private FinanceApprovalStatus financeApprovalStatus;
    private List<String> failureMessages;
}
