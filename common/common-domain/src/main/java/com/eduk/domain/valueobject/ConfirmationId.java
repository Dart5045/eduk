package com.eduk.domain.valueobject;

import java.util.UUID;

public class ConfirmationId extends BaseId<UUID> {
    protected ConfirmationId(UUID value) {
        super(value);
    }
}
