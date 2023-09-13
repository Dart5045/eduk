package com.eduk.finance.service.dataaccess.finance.outbox.repository;

import com.eduk.finance.service.dataaccess.finance.outbox.entity.ConfirmationOutboxEntity;
import com.eduk.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfirmationOutboxJpaRepository extends JpaRepository<ConfirmationOutboxEntity, UUID> {

    Optional<List<ConfirmationOutboxEntity>> findByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

    Optional<ConfirmationOutboxEntity> findByTypeAndSagaIdAndOutboxStatus(String type, UUID sagaId, OutboxStatus outboxStatus);

    void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

}
