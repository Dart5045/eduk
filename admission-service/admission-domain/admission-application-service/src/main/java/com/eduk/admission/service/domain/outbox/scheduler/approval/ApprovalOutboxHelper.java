package com.eduk.admission.service.domain.outbox.scheduler.approval;

import com.eduk.admission.service.domain.exception.ConfirmationDomainException;
import com.eduk.admission.service.domain.outbox.model.approval.ConfirmationApprovalOutboxMessage;
import com.eduk.admission.service.domain.ports.output.repository.ApprovalOutboxRepository;
import com.eduk.domain.event.payload.ConfirmationApprovalEventPayload;
import com.eduk.domain.valueobject.ConfirmationStatus;
import com.eduk.outbox.OutboxStatus;
import com.eduk.saga.SagaStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.eduk.saga.confirmaiton.SagaConstants.CONFIRMATION_SAGA_NAME;

@Slf4j
@Component
public class ApprovalOutboxHelper {

    private final ApprovalOutboxRepository approvalOutboxRepository;
    private final ObjectMapper objectMapper;

    public ApprovalOutboxHelper(ApprovalOutboxRepository approvalOutboxRepository, ObjectMapper objectMapper) {
        this.approvalOutboxRepository = approvalOutboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public Optional<List<ConfirmationApprovalOutboxMessage>>
    getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return approvalOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(CONFIRMATION_SAGA_NAME,
                outboxStatus,
                sagaStatus);
    }

    @Transactional(readOnly = true)
    public Optional<ConfirmationApprovalOutboxMessage>
    getApprovalOutboxMessageBySagaIdAndSagaStatus(UUID sagaId, SagaStatus... sagaStatus) {
        return approvalOutboxRepository.findByTypeAndSagaIdAndSagaStatus(CONFIRMATION_SAGA_NAME, sagaId, sagaStatus);
    }

    @Transactional
    public void save(ConfirmationApprovalOutboxMessage confirmationApprovalOutboxMessage) {
        ConfirmationApprovalOutboxMessage response = approvalOutboxRepository.save(confirmationApprovalOutboxMessage);
        if (response == null) {
            log.error("Could not save ConfirmationApprovalOutboxMessage with outbox id: {}",
                    confirmationApprovalOutboxMessage.getId());
            throw new ConfirmationDomainException("Could not save ConfirmationApprovalOutboxMessage with outbox id: " +
                    confirmationApprovalOutboxMessage.getId());
        }
        log.info("ConfirmationApprovalOutboxMessage saved with outbox id: {}", confirmationApprovalOutboxMessage.getId());
    }

    @Transactional
    public void saveApprovalOutboxMessage(ConfirmationApprovalEventPayload confirmationApprovalEventPayload,
                                          ConfirmationStatus confirmationStatus,
                                          SagaStatus sagaStatus,
                                          OutboxStatus outboxStatus,
                                          UUID sagaId) {
        save(ConfirmationApprovalOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(confirmationApprovalEventPayload.getCreatedAt())
                .type(CONFIRMATION_SAGA_NAME)
                .payload(createPayload(confirmationApprovalEventPayload))
                .confirmationStatus(confirmationStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build());
    }

    @Transactional
    public void deleteApprovalOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
                                                                       SagaStatus... sagaStatus) {
        approvalOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(CONFIRMATION_SAGA_NAME, outboxStatus, sagaStatus);
    }

    private String createPayload(ConfirmationApprovalEventPayload confirmationApprovalEventPayload) {
        try {
            return objectMapper.writeValueAsString(confirmationApprovalEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create ConfirmationApprovalEventPayload for confirmation id: {}",
                    confirmationApprovalEventPayload.getConfirmationId(), e);
            throw new ConfirmationDomainException("Could not create ConfirmationApprovalEventPayload for confirmation id: " +
                    confirmationApprovalEventPayload.getConfirmationId(), e);
        }
    }

}
