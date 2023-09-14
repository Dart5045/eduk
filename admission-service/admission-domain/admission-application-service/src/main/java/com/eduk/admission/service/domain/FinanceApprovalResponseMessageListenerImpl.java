package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.dto.message.FinanceApprovalResponse;
import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.ports.input.message.listener.financeapproval.FinanceApprovalResponseMessageListener;
import com.eduk.admission.service.domain.event.ConfirmationCancelledEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class FinanceApprovalResponseMessageListenerImpl implements FinanceApprovalResponseMessageListener {

    private final ConfirmationApprovalSaga confirmationApprovalSaga;

    public FinanceApprovalResponseMessageListenerImpl(ConfirmationApprovalSaga confirmationApprovalSaga) {
        this.confirmationApprovalSaga = confirmationApprovalSaga;
    }

    @Override
    public void confirmationApproved(FinanceApprovalResponse restaurantApprovalResponse) {
        confirmationApprovalSaga.process(restaurantApprovalResponse);
        log.info("Confirmation is approved for confirmation id: {}", restaurantApprovalResponse.getConfirmationId());
    }

    @Override
    public void confirmationRejected(FinanceApprovalResponse restaurantApprovalResponse) {
          confirmationApprovalSaga.rollback(restaurantApprovalResponse);
          log.info("Confirmation approval Saga rollback operation is completed for confirmation id: {} with failure messages: {}",
                  restaurantApprovalResponse.getConfirmationId(),
                  String.join(Confirmation.FAILURE_MESSAGE_DELIMITER, restaurantApprovalResponse.getFailureMessages()));
    }
}
