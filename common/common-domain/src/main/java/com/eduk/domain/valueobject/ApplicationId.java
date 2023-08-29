package com.eduk.domain.valueobject;

import java.util.UUID;

public class ApplicationId extends BaseId<UUID> {
    protected ApplicationId(UUID value) {
        super(value);
    }
}
