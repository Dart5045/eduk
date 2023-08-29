package com.eduk.payment.domain.entity.entity;

import com.eduk.domain.entity.BaseEntity;
import com.eduk.domain.valueobject.PaymentId;
import com.eduk.payment.domain.entity.valueobject.PaymentFeeId;

public class PaymentFee extends BaseEntity<PaymentFeeId> {
    private PaymentId paymentId;
}
