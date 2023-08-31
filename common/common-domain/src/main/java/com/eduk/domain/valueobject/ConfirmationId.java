package com.eduk.domain.valueobject;

import java.util.UUID;

public class ConfirmationId extends BaseId<UUID> {
    public ConfirmationId(UUID value) {
        super(value);
    }
}
