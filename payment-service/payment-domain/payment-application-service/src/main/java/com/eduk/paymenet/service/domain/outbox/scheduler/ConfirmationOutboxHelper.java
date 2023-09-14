package com.eduk.paymenet.service.domain.outbox.scheduler;

import com.eduk.domain.valueobject.PaymentStatus;
import com.eduk.outbox.OutboxStatus;
import com.eduk.paymenet.service.domain.outbox.model.ConfirmationEventPayload;
import com.eduk.paymenet.service.domain.outbox.model.ConfirmationOutboxMessage;
import com.eduk.paymenet.service.domain.ports.output.repository.ConfirmationOutboxRepository;
import com.eduk.payment.service.domain.exception.PaymentDomainException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.eduk.domain.DomainConstants.UTC;
import static com.eduk.saga.confirmaiton.SagaConstants.CONFIRMATION_SAGA_NAME;

@Slf4j
@Component
public class ConfirmationOutboxHelper {

    private final ConfirmationOutboxRepository confirmationOutboxRepository;
    private final ObjectMapper objectMapper;

    public ConfirmationOutboxHelper(ConfirmationOutboxRepository confirmationOutboxRepository, ObjectMapper objectMapper) {
        this.confirmationOutboxRepository = confirmationOutboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public Optional<ConfirmationOutboxMessage> getCompletedConfirmationOutboxMessageBySagaIdAndPaymentStatus(UUID sagaId,
                                                                                                             PaymentStatus
                                                                                                       paymentStatus) {
        return confirmationOutboxRepository.findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(CONFIRMATION_SAGA_NAME, sagaId,
                paymentStatus, OutboxStatus.COMPLETED);
    }

    @Transactional(readOnly = true)
    public Optional<List<ConfirmationOutboxMessage>> getConfirmationOutboxMessageByOutboxStatus(OutboxStatus outboxStatus) {
        return confirmationOutboxRepository.findByTypeAndOutboxStatus(CONFIRMATION_SAGA_NAME, outboxStatus);
    }

    @Transactional
    public void deleteConfirmationOutboxMessageByOutboxStatus(OutboxStatus outboxStatus) {
        confirmationOutboxRepository.deleteByTypeAndOutboxStatus(CONFIRMATION_SAGA_NAME, outboxStatus);
    }

    @Transactional
    public void saveConfirmationOutboxMessage(ConfirmationEventPayload confirmationEventPayload,
                                       PaymentStatus paymentStatus,
                                       OutboxStatus outboxStatus,
                                       UUID sagaId) {
        save(ConfirmationOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(confirmationEventPayload.getCreatedAt())
                .processedAt(ZonedDateTime.now(ZoneId.of(UTC)))
                .type(CONFIRMATION_SAGA_NAME)
                .payload(createPayload(confirmationEventPayload))
                .paymentStatus(paymentStatus)
                .outboxStatus(outboxStatus)
                .build());
    }

    @Transactional
    public void updateOutboxMessage(ConfirmationOutboxMessage confirmationOutboxMessage, OutboxStatus outboxStatus) {
        confirmationOutboxMessage.setOutboxStatus(outboxStatus);
        save(confirmationOutboxMessage);
        log.info("Confirmation outbox table status is updated as: {}", outboxStatus.name());
    }

    private String createPayload(ConfirmationEventPayload confirmationEventPayload) {
        try {
            return objectMapper.writeValueAsString(confirmationEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create ConfirmationEventPayload json!", e);
            throw new PaymentDomainException("Could not create ConfirmationEventPayload json!", e);
        }
    }

    private void save(ConfirmationOutboxMessage confirmationOutboxMessage) {
        ConfirmationOutboxMessage response = confirmationOutboxRepository.save(confirmationOutboxMessage);
        if (response == null) {
            log.error("Could not save ConfirmationOutboxMessage!");
            throw new PaymentDomainException("Could not save ConfirmationOutboxMessage!");
        }
        log.info("ConfirmationOutboxMessage is saved with id: {}", confirmationOutboxMessage.getId());
    }
}
