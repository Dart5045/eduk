package com.eduk.paymenet.service.domain.dto;

import com.eduk.payment.service.domain.valueobject.PaymentConfirmationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private String id;
    private String sagaId;
    private String confirmationId;
    private String applicationId;
    private final BigDecimal price;
    private Instant createdAt;
    private PaymentConfirmationStatus paymentConfirmationStatus;

    public void setPaymentConfirmationStatus(PaymentConfirmationStatus paymentConfirmationStatus) {
        this.paymentConfirmationStatus = paymentConfirmationStatus;
    }
}
