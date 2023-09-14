package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.exception.ConfirmationDomainException;
import com.eduk.admission.service.domain.mapper.ConfirmationDataMapper;
import com.eduk.admission.service.domain.outbox.model.approval.ConfirmationApprovalOutboxMessage;
import com.eduk.admission.service.domain.outbox.model.payment.ConfirmationPaymentOutboxMessage;
import com.eduk.admission.service.domain.outbox.scheduler.approval.ApprovalOutboxHelper;
import com.eduk.admission.service.domain.outbox.scheduler.payment.PaymentOutboxHelper;
import com.eduk.admission.service.domain.dto.message.FinanceApprovalResponse;
import com.eduk.admission.service.domain.event.ConfirmationCancelledEvent;
import com.eduk.domain.event.EmptyEvent;
import com.eduk.domain.valueobject.ConfirmationStatus;
import com.eduk.outbox.OutboxStatus;
import com.eduk.saga.SagaStatus;
import com.eduk.saga.SagaStep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.eduk.domain.DomainConstants.UTC;

@Slf4j
@Component
public class ConfirmationApprovalSaga implements SagaStep<FinanceApprovalResponse> {
    private final ConfirmationDomainService confirmationDomainService;
    private final ConfirmationSagaHelper confirmationSagaHelper;
    private final PaymentOutboxHelper paymentOutboxHelper;
    private final ApprovalOutboxHelper approvalOutboxHelper;
    private final ConfirmationDataMapper confirmationDataMapper;

    public ConfirmationApprovalSaga(ConfirmationDomainService confirmationDomainService,
                             ConfirmationSagaHelper confirmationSagaHelper,
                             PaymentOutboxHelper paymentOutboxHelper,
                             ApprovalOutboxHelper approvalOutboxHelper,
                             ConfirmationDataMapper confirmationDataMapper) {
        this.confirmationDomainService = confirmationDomainService;
        this.confirmationSagaHelper = confirmationSagaHelper;
        this.paymentOutboxHelper = paymentOutboxHelper;
        this.approvalOutboxHelper = approvalOutboxHelper;
        this.confirmationDataMapper = confirmationDataMapper;
    }

    @Override
    @Transactional
    public void process(FinanceApprovalResponse financeApprovalResponse) {
        Optional<ConfirmationApprovalOutboxMessage> confirmationApprovalOutboxMessageResponse =
                approvalOutboxHelper.getApprovalOutboxMessageBySagaIdAndSagaStatus(
                        UUID.fromString(financeApprovalResponse.getSagaId()),
                        SagaStatus.PROCESSING);

        if (confirmationApprovalOutboxMessageResponse.isEmpty()) {
            log.info("An outbox message with saga id: {} is already processed!",
                    financeApprovalResponse.getSagaId());
            return;
        }

        ConfirmationApprovalOutboxMessage confirmationApprovalOutboxMessage = confirmationApprovalOutboxMessageResponse.get();

        Confirmation confirmation = approveConfirmation(financeApprovalResponse);

        SagaStatus sagaStatus = confirmationSagaHelper.confirmationStatusToSagaStatus(confirmation.getConfirmationStatus());

        approvalOutboxHelper.save(getUpdatedApprovalOutboxMessage(confirmationApprovalOutboxMessage,
                confirmation.getConfirmationStatus(), sagaStatus));

        paymentOutboxHelper.save(getUpdatedPaymentOutboxMessage(financeApprovalResponse.getSagaId(),
                confirmation.getConfirmationStatus(), sagaStatus));

        log.info("Confirmation with id: {} is approved", confirmation.getId().getValue());
    }

    @Override
    @Transactional
    public void rollback(FinanceApprovalResponse financeApprovalResponse) {
        Optional<ConfirmationApprovalOutboxMessage> confirmationApprovalOutboxMessageResponse =
                approvalOutboxHelper.getApprovalOutboxMessageBySagaIdAndSagaStatus(
                        UUID.fromString(financeApprovalResponse.getSagaId()),
                        SagaStatus.PROCESSING);

        if (confirmationApprovalOutboxMessageResponse.isEmpty()) {
            log.info("An outbox message with saga id: {} is already roll backed!",
                    financeApprovalResponse.getSagaId());
            return;
        }

        ConfirmationApprovalOutboxMessage confirmationApprovalOutboxMessage = confirmationApprovalOutboxMessageResponse.get();

        ConfirmationCancelledEvent domainEvent = rollbackConfirmation(financeApprovalResponse);

        SagaStatus sagaStatus = confirmationSagaHelper.confirmationStatusToSagaStatus(domainEvent.getConfirmation().getConfirmationStatus());

        approvalOutboxHelper.save(getUpdatedApprovalOutboxMessage(confirmationApprovalOutboxMessage,
                domainEvent.getConfirmation().getConfirmationStatus(), sagaStatus));

        paymentOutboxHelper.savePaymentOutboxMessage(confirmationDataMapper
                        .confirmationCancelledEventToConfirmationPaymentEventPayload(domainEvent),
                domainEvent.getConfirmation().getConfirmationStatus(),
                sagaStatus,
                OutboxStatus.STARTED,
                UUID.fromString(financeApprovalResponse.getSagaId()));

        log.info("Confirmation with id: {} is cancelling", domainEvent.getConfirmation().getId().getValue());
    }

    private Confirmation approveConfirmation(FinanceApprovalResponse financeApprovalResponse) {
        log.info("Approving confirmation with id: {}", financeApprovalResponse.getConfirmationId());
        Confirmation confirmation = confirmationSagaHelper.findConfirmation(financeApprovalResponse.getConfirmationId());
        confirmationDomainService.approveConfirmation(confirmation);
        confirmationSagaHelper.saveConfirmation(confirmation);
        return confirmation;
    }

    private ConfirmationApprovalOutboxMessage getUpdatedApprovalOutboxMessage(ConfirmationApprovalOutboxMessage
                                                                               confirmationApprovalOutboxMessage,
                                                                       ConfirmationStatus
                                                                               confirmationStatus,
                                                                       SagaStatus
                                                                               sagaStatus) {
        confirmationApprovalOutboxMessage.setProcessedAt(ZonedDateTime.now(ZoneId.of(UTC)));
        confirmationApprovalOutboxMessage.setConfirmationStatus(confirmationStatus);
        confirmationApprovalOutboxMessage.setSagaStatus(sagaStatus);
        return confirmationApprovalOutboxMessage;
    }

    private ConfirmationPaymentOutboxMessage getUpdatedPaymentOutboxMessage(String sagaId,
                                                                            ConfirmationStatus confirmationStatus,
                                                                            SagaStatus sagaStatus) {
        Optional<ConfirmationPaymentOutboxMessage> confirmationPaymentOutboxMessageResponse = paymentOutboxHelper
                .getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID.fromString(sagaId), SagaStatus.PROCESSING);
        if (confirmationPaymentOutboxMessageResponse.isEmpty()) {
            throw new ConfirmationDomainException("Payment outbox message cannot be found in " +
                    SagaStatus.PROCESSING.name() + " state");
        }
        ConfirmationPaymentOutboxMessage confirmationPaymentOutboxMessage = confirmationPaymentOutboxMessageResponse.get();
        confirmationPaymentOutboxMessage.setProcessedAt(ZonedDateTime.now(ZoneId.of(UTC)));
        confirmationPaymentOutboxMessage.setConfirmationStatus(confirmationStatus);
        confirmationPaymentOutboxMessage.setSagaStatus(sagaStatus);
        return confirmationPaymentOutboxMessage;
    }

    private ConfirmationCancelledEvent rollbackConfirmation(FinanceApprovalResponse financeApprovalResponse) {
        log.info("Cancelling confirmation with id: {}", financeApprovalResponse.getConfirmationId());
        Confirmation confirmation = confirmationSagaHelper.findConfirmation(financeApprovalResponse.getConfirmationId());
        ConfirmationCancelledEvent domainEvent
                = confirmationDomainService.cancelConfirmationPayment(confirmation,
                financeApprovalResponse.getFailureMessages());
        confirmationSagaHelper.saveConfirmation(confirmation);
        return domainEvent;
    }
}
