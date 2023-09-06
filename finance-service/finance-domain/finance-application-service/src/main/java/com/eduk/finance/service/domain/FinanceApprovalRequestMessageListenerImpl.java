package com.eduk.finance.service.domain;

import com.eduk.finance.service.domain.dto.FinanceApprovalRequest;
import com.eduk.finance.service.domain.event.ConfirmationApprovalEvent;
import com.eduk.finance.service.domain.ports.input.message.listener.FinanceApprovalRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FinanceApprovalRequestMessageListenerImpl implements FinanceApprovalRequestMessageListener {

    private final FinanceApprovalRequestHelper financeApprovalRequestHelper;

    public FinanceApprovalRequestMessageListenerImpl(FinanceApprovalRequestHelper
                                                                financeApprovalRequestHelper) {
        this.financeApprovalRequestHelper = financeApprovalRequestHelper;
    }

    @Override
    public void approveConfirmation(FinanceApprovalRequest financeApprovalRequest) {
        ConfirmationApprovalEvent orderApprovalEvent =
                financeApprovalRequestHelper.persistConfirmationApproval(financeApprovalRequest);
        orderApprovalEvent.fire();
    }
}
