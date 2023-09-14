package com.eduk.payment.service.dataaccess.outbox.repository;

import com.eduk.domain.valueobject.PaymentStatus;
import com.eduk.outbox.OutboxStatus;
import com.eduk.payment.service.dataaccess.outbox.entity.ConfirmationOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfirmationOutboxJpaRepository extends JpaRepository<ConfirmationOutboxEntity, UUID> {

    Optional<List<ConfirmationOutboxEntity>> findByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

    Optional<ConfirmationOutboxEntity> findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(String type,
                                                                                          UUID sagaId,
                                                                                          PaymentStatus paymentStatus, OutboxStatus outboxStatus);
    void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

}
