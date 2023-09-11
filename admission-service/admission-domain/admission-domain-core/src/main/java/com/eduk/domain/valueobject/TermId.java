package com.eduk.domain.valueobject;

import java.util.UUID;

public class TermId extends BaseId<UUID> {
    public TermId(UUID value) {
        super(value);
    }
}
