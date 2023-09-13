package com.eduk.finance.service.domain.ports.output.repository;


import com.eduk.finance.service.domain.outbox.model.ConfirmationOutboxMessage;
import com.eduk.outbox.OutboxStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConfirmationOutboxRepository {

    ConfirmationOutboxMessage save(ConfirmationOutboxMessage confirmationOutboxMessage);

    Optional<List<ConfirmationOutboxMessage>> findByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

    Optional<ConfirmationOutboxMessage> findByTypeAndSagaIdAndOutboxStatus(String type, UUID sagaId,
                                                                    OutboxStatus outboxStatus);

    void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

}
