package com.eduk.finance.service.domain.ports.input.message.listener;


import com.eduk.finance.service.domain.dto.FinanceApprovalRequest;

public interface FinanceApprovalRequestMessageListener {
    void approveConfirmation(FinanceApprovalRequest financeApprovalRequest);
}
