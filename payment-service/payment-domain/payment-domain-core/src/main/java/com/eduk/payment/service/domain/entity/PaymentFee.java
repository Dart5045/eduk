package com.eduk.payment.service.domain.entity;

import com.eduk.domain.entity.BaseEntity;
import com.eduk.payment.service.domain.valueobject.PaymentId;
import com.eduk.payment.service.domain.valueobject.PaymentFeeId;

public class PaymentFee extends BaseEntity<PaymentFeeId> {
    private PaymentId paymentId;
}
