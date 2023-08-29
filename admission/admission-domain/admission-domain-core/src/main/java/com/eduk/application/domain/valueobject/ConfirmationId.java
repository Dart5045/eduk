package com.eduk.application.domain.valueobject;

import com.eduk.domain.entity.BaseEntity;
import com.eduk.domain.valueobject.BaseId;

import java.util.UUID;

public class ConfirmationId extends BaseId<UUID> {

    protected ConfirmationId(UUID value) {
        super(value);
    }
}
