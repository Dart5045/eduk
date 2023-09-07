package com.eduk.payment.service.domain.valueobject;

import com.eduk.admission.service.domain.valueobject.BaseId;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
