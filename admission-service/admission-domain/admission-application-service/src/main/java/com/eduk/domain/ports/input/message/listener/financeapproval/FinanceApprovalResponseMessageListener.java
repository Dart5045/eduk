package com.eduk.domain.ports.input.message.listener.financeapproval;

import com.eduk.domain.dto.message.FinanceApprovalResponse;

public interface FinanceApprovalResponseMessageListener {
    void confirmationApproved(FinanceApprovalResponse financeApprovalResponse);
    void confirmationRejected(FinanceApprovalResponse financeApprovalResponse);
}
