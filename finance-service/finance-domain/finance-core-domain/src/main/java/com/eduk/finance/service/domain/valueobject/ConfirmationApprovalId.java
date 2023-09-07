package com.eduk.finance.service.domain.valueobject;


import com.eduk.admission.service.domain.valueobject.BaseId;

import java.util.UUID;

public class ConfirmationApprovalId extends BaseId<UUID> {
    public ConfirmationApprovalId(UUID value) {
        super(value);
    }
}
