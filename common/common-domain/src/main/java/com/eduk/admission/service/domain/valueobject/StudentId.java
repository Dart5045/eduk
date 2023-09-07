package com.eduk.admission.service.domain.valueobject;

import java.util.UUID;

public class StudentId extends BaseId<UUID> {
    protected StudentId(UUID value) {
        super(value);
    }
}
