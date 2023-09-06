package com.eduk.service.domain.ports.input.message.listener.financeapproval;

import com.eduk.service.domain.dto.message.FinanceApprovalResponse;

public interface FinanceApprovalResponseMessageListener {
    void confirmationApproved(FinanceApprovalResponse financeApprovalResponse);
    void confirmationRejected(FinanceApprovalResponse financeApprovalResponse);
}
