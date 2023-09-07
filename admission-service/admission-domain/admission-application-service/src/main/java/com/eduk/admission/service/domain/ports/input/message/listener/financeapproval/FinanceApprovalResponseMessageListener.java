package com.eduk.admission.service.domain.ports.input.message.listener.financeapproval;

import com.eduk.admission.service.domain.dto.message.FinanceApprovalResponse;

public interface FinanceApprovalResponseMessageListener {
    void confirmationApproved(FinanceApprovalResponse financeApprovalResponse);
    void confirmationRejected(FinanceApprovalResponse financeApprovalResponse);
}
