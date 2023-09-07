package com.eduk.payment.service.domain.valueobject;

import com.eduk.admission.service.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditHistoryId extends BaseId<UUID> {
    public CreditHistoryId(UUID value) {
        super(value);
    }
}
