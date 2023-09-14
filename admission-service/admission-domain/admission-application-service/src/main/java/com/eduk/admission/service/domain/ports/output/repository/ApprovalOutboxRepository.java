package com.eduk.admission.service.domain.ports.output.repository;


import com.eduk.admission.service.domain.outbox.model.approval.ConfirmationApprovalOutboxMessage;
import com.eduk.outbox.OutboxStatus;
import com.eduk.saga.SagaStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApprovalOutboxRepository {

    ConfirmationApprovalOutboxMessage save(ConfirmationApprovalOutboxMessage confirmationApprovalOutboxMessage);

    Optional<List<ConfirmationApprovalOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String type,
                                                                                     OutboxStatus outboxStatus,
                                                                                     SagaStatus... sagaStatus);
    Optional<ConfirmationApprovalOutboxMessage> findByTypeAndSagaIdAndSagaStatus(String type,
                                                                         UUID sagaId,
                                                                         SagaStatus... sagaStatus);
    void deleteByTypeAndOutboxStatusAndSagaStatus(String type,
                                                  OutboxStatus outboxStatus,
                                                  SagaStatus... sagaStatus);
}
