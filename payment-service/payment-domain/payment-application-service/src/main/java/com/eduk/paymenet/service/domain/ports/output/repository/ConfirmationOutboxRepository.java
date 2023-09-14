package com.eduk.paymenet.service.domain.ports.output.repository;

import com.eduk.domain.valueobject.PaymentStatus;
import com.eduk.outbox.OutboxStatus;
import com.eduk.paymenet.service.domain.outbox.model.ConfirmationOutboxMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConfirmationOutboxRepository {
    ConfirmationOutboxMessage save(ConfirmationOutboxMessage confirmationOutboxMessage);

    Optional<List<ConfirmationOutboxMessage>> findByTypeAndOutboxStatus(String type, OutboxStatus status);

    Optional<ConfirmationOutboxMessage> findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(String type,
                                                                                           UUID sagaId,
                                                                                           PaymentStatus paymentStatus,
                                                                                           OutboxStatus outboxStatus);
    void deleteByTypeAndOutboxStatus(String type, OutboxStatus status);
}
