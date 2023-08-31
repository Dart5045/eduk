package com.eduk.service.domain.ports.input.message.listener.financeapproval;

import com.eduk.service.domain.dto.message.FinanceApprovalResponse;
import com.eduk.service.domain.dto.message.PaymentResponse;

public interface FinanceApprovalResponseMessageListener {
    void paymentApproved(FinanceApprovalResponse financeApprovalResponse);
    void paymentRejected(FinanceApprovalResponse financeApprovalResponse);
}
