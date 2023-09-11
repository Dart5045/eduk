package com.eduk.admission.service.domain;

import com.eduk.domain.event.EmptyEvent;
import com.eduk.domain.entity.Confirmation;
import com.eduk.domain.event.ConfirmationCancelledEvent;
import com.eduk.admission.service.domain.dto.message.FinanceApprovalResponse;
import com.eduk.admission.service.domain.ports.output.message.publisher.payment.ConfirmationCancelledPaymentRequestMessagePublisher;
import com.eduk.saga.SagaStep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class ConfirmationApprovalSaga implements SagaStep<FinanceApprovalResponse, EmptyEvent, ConfirmationCancelledEvent> {

    private final ConfirmationDomainService confirmationDomainService;
    private final ConfirmationSagaHelper confirmationSagaHelper;
    private final ConfirmationCancelledPaymentRequestMessagePublisher confirmationCancelledPaymentRequestMessagePublisher;

    public ConfirmationApprovalSaga(ConfirmationDomainService confirmationDomainService, ConfirmationSagaHelper confirmationSagaHelper, ConfirmationCancelledPaymentRequestMessagePublisher confirmationCancelledPaymentRequestMessagePublisher) {
        this.confirmationDomainService = confirmationDomainService;
        this.confirmationSagaHelper = confirmationSagaHelper;
        this.confirmationCancelledPaymentRequestMessagePublisher = confirmationCancelledPaymentRequestMessagePublisher;
    }


    @Override
    @Transactional
    public EmptyEvent process(FinanceApprovalResponse financeApprovalResponse) {
        log.info("Approving confirmation with id: {}", financeApprovalResponse.getConfirmationId());
        Confirmation confirmation = confirmationSagaHelper.findConfirmation(financeApprovalResponse.getConfirmationId());
        confirmationDomainService.approveConfirmation(confirmation);
        confirmationSagaHelper.saveConfirmation(confirmation);
        log.info("Confirmation with id: {} is approved", confirmation.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public ConfirmationCancelledEvent rollback(FinanceApprovalResponse financeApprovalResponse) {
        log.info("Cancelling confirmation with id: {}", financeApprovalResponse.getConfirmationId());
        Confirmation confirmation = confirmationSagaHelper.findConfirmation(financeApprovalResponse.getConfirmationId());
        ConfirmationCancelledEvent domainEvent = confirmationDomainService.cancelConfirmationPayment(confirmation,
                financeApprovalResponse.getFailureMessages(),
                confirmationCancelledPaymentRequestMessagePublisher);
        confirmationSagaHelper.saveConfirmation(confirmation);
        log.info("Confirmation with id: {} is cancelling", confirmation.getId().getValue());
        return domainEvent;
    }
}
