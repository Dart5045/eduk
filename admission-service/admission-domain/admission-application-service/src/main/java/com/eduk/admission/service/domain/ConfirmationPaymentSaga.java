package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.dto.message.PaymentResponse;
import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.exception.ConfirmationDomainException;
import com.eduk.admission.service.domain.mapper.ConfirmationDataMapper;
import com.eduk.admission.service.domain.outbox.model.approval.ConfirmationApprovalOutboxMessage;
import com.eduk.admission.service.domain.outbox.model.payment.ConfirmationPaymentOutboxMessage;
import com.eduk.admission.service.domain.outbox.scheduler.approval.ApprovalOutboxHelper;
import com.eduk.admission.service.domain.outbox.scheduler.payment.PaymentOutboxHelper;
import com.eduk.admission.service.domain.ports.output.repository.ConfirmationRepository;
import com.eduk.admission.service.domain.event.ConfirmationPaidEvent;
import com.eduk.admission.service.domain.exception.ConfirmationNotFoundException;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.domain.valueobject.ConfirmationStatus;
import com.eduk.domain.valueobject.PaymentStatus;
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
public class ConfirmationPaymentSaga implements SagaStep<PaymentResponse> {


    private final ConfirmationDomainService confirmationDomainService;
    private final ConfirmationRepository confirmationRepository;
    private final PaymentOutboxHelper paymentOutboxHelper;
    private final ApprovalOutboxHelper approvalOutboxHelper;
    private final ConfirmationSagaHelper confirmationSagaHelper;
    private final ConfirmationDataMapper confirmationDataMapper;

    public ConfirmationPaymentSaga(ConfirmationDomainService confirmationDomainService,
                            ConfirmationRepository confirmationRepository,
                            PaymentOutboxHelper paymentOutboxHelper,
                            ApprovalOutboxHelper approvalOutboxHelper,
                            ConfirmationSagaHelper confirmationSagaHelper,
                            ConfirmationDataMapper confirmationDataMapper) {
        this.confirmationDomainService = confirmationDomainService;
        this.confirmationRepository = confirmationRepository;
        this.paymentOutboxHelper = paymentOutboxHelper;
        this.approvalOutboxHelper = approvalOutboxHelper;
        this.confirmationSagaHelper = confirmationSagaHelper;
        this.confirmationDataMapper = confirmationDataMapper;
    }

    @Override
    @Transactional
    public void process(PaymentResponse paymentResponse) {
        Optional<ConfirmationPaymentOutboxMessage> confirmationPaymentOutboxMessageResponse =
                paymentOutboxHelper.getPaymentOutboxMessageBySagaIdAndSagaStatus(
                        UUID.fromString(paymentResponse.getSagaId()),
                        SagaStatus.STARTED);

        if (confirmationPaymentOutboxMessageResponse.isEmpty()) {
            log.info("An outbox message with saga id: {} is already processed!", paymentResponse.getSagaId());
            return;
        }

        ConfirmationPaymentOutboxMessage confirmationPaymentOutboxMessage = confirmationPaymentOutboxMessageResponse.get();

        ConfirmationPaidEvent domainEvent = completePaymentForConfirmation(paymentResponse);

        SagaStatus sagaStatus = confirmationSagaHelper.confirmationStatusToSagaStatus(domainEvent.getConfirmation().getConfirmationStatus());

        paymentOutboxHelper.save(getUpdatedPaymentOutboxMessage(confirmationPaymentOutboxMessage,
                domainEvent.getConfirmation().getConfirmationStatus(), sagaStatus));

        approvalOutboxHelper
                .saveApprovalOutboxMessage(confirmationDataMapper.confirmationPaidEventToConfirmationApprovalEventPayload(domainEvent),
                        domainEvent.getConfirmation().getConfirmationStatus(),
                        sagaStatus,
                        OutboxStatus.STARTED,
                        UUID.fromString(paymentResponse.getSagaId()));

        log.info("Confirmation with id: {} is paid", domainEvent.getConfirmation().getId().getValue());
    }

    @Override
    @Transactional
    public void rollback(PaymentResponse paymentResponse) {

        Optional<ConfirmationPaymentOutboxMessage> confirmationPaymentOutboxMessageResponse =
                paymentOutboxHelper.getPaymentOutboxMessageBySagaIdAndSagaStatus(
                        UUID.fromString(paymentResponse.getSagaId()),
                        getCurrentSagaStatus(paymentResponse.getPaymentStatus()));

        if (confirmationPaymentOutboxMessageResponse.isEmpty()) {
            log.info("An outbox message with saga id: {} is already roll backed!", paymentResponse.getSagaId());
            return;
        }

        ConfirmationPaymentOutboxMessage confirmationPaymentOutboxMessage = confirmationPaymentOutboxMessageResponse.get();

        Confirmation confirmation = rollbackPaymentForConfirmation(paymentResponse);

        SagaStatus sagaStatus = confirmationSagaHelper.confirmationStatusToSagaStatus(confirmation.getConfirmationStatus());

        paymentOutboxHelper.save(getUpdatedPaymentOutboxMessage(confirmationPaymentOutboxMessage,
                confirmation.getConfirmationStatus(), sagaStatus));

        if (paymentResponse.getPaymentStatus() == PaymentStatus.CANCELLED) {
            approvalOutboxHelper.save(getUpdatedApprovalOutboxMessage(paymentResponse.getSagaId(),
                    confirmation.getConfirmationStatus(), sagaStatus));
        }

        log.info("Confirmation with id: {} is cancelled", confirmation.getId().getValue());
    }

    private Confirmation findConfirmation(String confirmationId) {
        Optional<Confirmation> confirmationResponse = confirmationRepository.findById(new ConfirmationId(UUID.fromString(confirmationId)));
        if (confirmationResponse.isEmpty()) {
            log.error("Confirmation with id: {} could not be found!", confirmationId);
            throw new ConfirmationNotFoundException("Confirmation with id " + confirmationId + " could not be found!");
        }
        return confirmationResponse.get();
    }

    private ConfirmationPaymentOutboxMessage getUpdatedPaymentOutboxMessage(ConfirmationPaymentOutboxMessage
                                                                             confirmationPaymentOutboxMessage,
                                                                     ConfirmationStatus
                                                                             confirmationStatus,
                                                                     SagaStatus
                                                                             sagaStatus) {
        confirmationPaymentOutboxMessage.setProcessedAt(ZonedDateTime.now(ZoneId.of(UTC)));
        confirmationPaymentOutboxMessage.setConfirmationStatus(confirmationStatus);
        confirmationPaymentOutboxMessage.setSagaStatus(sagaStatus);
        return confirmationPaymentOutboxMessage;
    }

    private ConfirmationPaidEvent completePaymentForConfirmation(PaymentResponse paymentResponse) {
        log.info("Completing payment for confirmation with id: {}", paymentResponse.getConfirmationId());
        Confirmation confirmation = findConfirmation(paymentResponse.getConfirmationId());
        ConfirmationPaidEvent domainEvent = confirmationDomainService.payConfirmation(confirmation);
        confirmationRepository.save(confirmation);
        return domainEvent;
    }

    private SagaStatus[] getCurrentSagaStatus(PaymentStatus paymentStatus) {
        return switch (paymentStatus) {
            case COMPLETED -> new SagaStatus[] { SagaStatus.STARTED };
            case CANCELLED -> new SagaStatus[] { SagaStatus.PROCESSING };
            case FAILED -> new SagaStatus[] { SagaStatus.STARTED, SagaStatus.PROCESSING };
        };
    }

    private Confirmation rollbackPaymentForConfirmation(PaymentResponse paymentResponse) {
        log.info("Cancelling confirmation with id: {}", paymentResponse.getConfirmationId());
        Confirmation confirmation = findConfirmation(paymentResponse.getConfirmationId());
        confirmationDomainService.cancelConfirmation(confirmation, paymentResponse.getFailureMessages());
        confirmationRepository.save(confirmation);
        return confirmation;
    }

    private ConfirmationApprovalOutboxMessage getUpdatedApprovalOutboxMessage(String sagaId,
                                                                       ConfirmationStatus confirmationStatus,
                                                                       SagaStatus sagaStatus) {
        Optional<ConfirmationApprovalOutboxMessage> confirmationApprovalOutboxMessageResponse =
                approvalOutboxHelper.getApprovalOutboxMessageBySagaIdAndSagaStatus(
                        UUID.fromString(sagaId),
                        SagaStatus.COMPENSATING);
        if (confirmationApprovalOutboxMessageResponse.isEmpty()) {
            throw new ConfirmationDomainException("Approval outbox message could not be found in " +
                    SagaStatus.COMPENSATING.name() + " status!");
        }
        ConfirmationApprovalOutboxMessage confirmationApprovalOutboxMessage = confirmationApprovalOutboxMessageResponse.get();
        confirmationApprovalOutboxMessage.setProcessedAt(ZonedDateTime.now(ZoneId.of(UTC)));
        confirmationApprovalOutboxMessage.setConfirmationStatus(confirmationStatus);
        confirmationApprovalOutboxMessage.setSagaStatus(sagaStatus);
        return confirmationApprovalOutboxMessage;
    }
}
