package com.eduk.service.domain;

import com.eduk.application.domain.event.ConfirmationCancelledEvent;
import com.eduk.service.domain.dto.message.FinanceApprovalResponse;
import com.eduk.service.domain.ports.input.message.listener.financeapproval.FinanceApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import static com.eduk.application.domain.entity.Confirmation.FAILURE_MESSAGE_DELIMITER;

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
          ConfirmationCancelledEvent domainEvent = confirmationApprovalSaga.rollback(restaurantApprovalResponse);
          log.info("Publishing confirmation cancelled event for confirmation id: {} with failure messages: {}",
                  restaurantApprovalResponse.getConfirmationId(),
                  String.join(FAILURE_MESSAGE_DELIMITER, restaurantApprovalResponse.getFailureMessages()));
          domainEvent.fire();
    }
}
