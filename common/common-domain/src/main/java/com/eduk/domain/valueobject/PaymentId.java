package com.eduk.domain.valueobject;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    protected PaymentId(UUID value) {
        super(value);
    }
}
