package com.eduk.domain.valueobject;

import java.util.UUID;

public class StudentId extends BaseId<UUID> {
    public StudentId(UUID value) {
        super(value);
    }
}
