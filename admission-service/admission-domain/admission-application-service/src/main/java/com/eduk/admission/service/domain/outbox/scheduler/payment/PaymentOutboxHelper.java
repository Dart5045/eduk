package com.eduk.admission.service.domain.outbox.scheduler.payment;


import com.eduk.admission.service.domain.exception.ConfirmationDomainException;
import com.eduk.admission.service.domain.outbox.model.payment.ConfirmationPaymentOutboxMessage;
import com.eduk.admission.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.eduk.domain.event.payload.ConfirmationPaymentEventPayload;
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
public class PaymentOutboxHelper {

    private final PaymentOutboxRepository paymentOutboxRepository;
    private final ObjectMapper objectMapper;

    public PaymentOutboxHelper(PaymentOutboxRepository paymentOutboxRepository,
                               ObjectMapper objectMapper) {
        this.paymentOutboxRepository = paymentOutboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public Optional<List<ConfirmationPaymentOutboxMessage>> getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return paymentOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(CONFIRMATION_SAGA_NAME,
                outboxStatus,
                sagaStatus);
    }

    @Transactional(readOnly = true)
    public Optional<ConfirmationPaymentOutboxMessage> getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID sagaId,
                                                                                                   SagaStatus... sagaStatus) {
        return paymentOutboxRepository.findByTypeAndSagaIdAndSagaStatus(CONFIRMATION_SAGA_NAME, sagaId, sagaStatus);
    }

    @Transactional
    public void save(ConfirmationPaymentOutboxMessage confirmationPaymentOutboxMessage) {
        ConfirmationPaymentOutboxMessage response = paymentOutboxRepository.save(confirmationPaymentOutboxMessage);
        if (response == null) {
            log.error("Could not save ConfirmationPaymentOutboxMessage with outbox id: {}", confirmationPaymentOutboxMessage.getId());
            throw new ConfirmationDomainException("Could not save ConfirmationPaymentOutboxMessage with outbox id: " +
                    confirmationPaymentOutboxMessage.getId());
        }
        log.info("ConfirmationPaymentOutboxMessage saved with outbox id: {}", confirmationPaymentOutboxMessage.getId());
    }

    @Transactional
    public void savePaymentOutboxMessage(ConfirmationPaymentEventPayload paymentEventPayload,
                                         ConfirmationStatus confirmationStatus,
                                         SagaStatus sagaStatus,
                                         OutboxStatus outboxStatus,
                                         UUID sagaId) {
        save(ConfirmationPaymentOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentEventPayload.getCreatedAt())
                .type(CONFIRMATION_SAGA_NAME)
                .payload(createPayload(paymentEventPayload))
                .confirmationStatus(confirmationStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build());
    }

    @Transactional
    public void deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
                                                                      SagaStatus... sagaStatus) {
        paymentOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(CONFIRMATION_SAGA_NAME, outboxStatus, sagaStatus);
    }

    private String createPayload(ConfirmationPaymentEventPayload paymentEventPayload) {
        try {
            return objectMapper.writeValueAsString(paymentEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create ConfirmationPaymentEventPayload object for confirmation id: {}",
                    paymentEventPayload.getConfirmationId(), e);
            throw new ConfirmationDomainException("Could not create ConfirmationPaymentEventPayload object for confirmation id: " +
                    paymentEventPayload.getConfirmationId(), e);
        }
    }

}
