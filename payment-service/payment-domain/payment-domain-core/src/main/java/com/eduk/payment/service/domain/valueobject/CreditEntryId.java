package com.eduk.payment.service.domain.valueobject;

import com.eduk.admission.service.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditEntryId extends BaseId<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
