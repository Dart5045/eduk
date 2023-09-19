package com.eduk.finance.service.domain;

import com.eduk.domain.valueobject.ConfirmationApprovalStatus;
import com.eduk.finance.service.domain.dto.FinanceApprovalRequest;
import com.eduk.finance.service.domain.entity.Finance;
import com.eduk.finance.service.domain.event.ConfirmationApprovalEvent;
import com.eduk.finance.service.domain.event.ConfirmationApprovedEvent;
import com.eduk.finance.service.domain.event.ConfirmationRejectedEvent;
import com.eduk.finance.service.domain.ports.input.message.listener.FinanceApprovalRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.eduk.domain.DomainConstants.UTC;

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
        financeApprovalRequestHelper.persistConfirmationApproval(financeApprovalRequest);
    }
}
