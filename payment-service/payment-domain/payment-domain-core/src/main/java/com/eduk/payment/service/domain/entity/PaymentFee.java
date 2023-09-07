package com.eduk.payment.service.domain.entity;

import com.eduk.admission.service.domain.entity.BaseEntity;
import com.eduk.payment.service.domain.valueobject.PaymentFeeId;
import com.eduk.payment.service.domain.valueobject.PaymentId;

public class PaymentFee extends BaseEntity<PaymentFeeId> {
    private PaymentId paymentId;
}
