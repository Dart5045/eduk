package com.eduk.finance.service.domain.ports.output.repository;

import com.eduk.finance.service.domain.entity.ConfirmationApproval;

public interface ConfirmationApprovalRepository {
    ConfirmationApproval save(ConfirmationApproval orderApproval);
}
