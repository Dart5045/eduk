package com.eduk.admission.service.domain.valueobject;

import com.eduk.domain.valueobject.BaseId;

import java.util.UUID;

public class TermId extends BaseId<UUID> {
    public TermId(UUID value) {
        super(value);
    }
}
