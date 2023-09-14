package com.eduk.payment.service.dataaccess.outbox.adapter;

import com.eduk.domain.valueobject.PaymentStatus;
import com.eduk.outbox.OutboxStatus;
import com.eduk.paymenet.service.domain.outbox.model.ConfirmationOutboxMessage;
import com.eduk.paymenet.service.domain.ports.output.repository.ConfirmationOutboxRepository;
import com.eduk.payment.service.dataaccess.outbox.exception.ConfirmationOutboxNotFoundException;
import com.eduk.payment.service.dataaccess.outbox.mapper.ConfirmationOutboxDataAccessMapper;
import com.eduk.payment.service.dataaccess.outbox.repository.ConfirmationOutboxJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConfirmationOutboxRepositoryImpl implements ConfirmationOutboxRepository {

    private final ConfirmationOutboxJpaRepository confirmationOutboxJpaRepository;
    private final ConfirmationOutboxDataAccessMapper confirmationOutboxDataAccessMapper;

    public ConfirmationOutboxRepositoryImpl(ConfirmationOutboxJpaRepository confirmationOutboxJpaRepository,
                                            ConfirmationOutboxDataAccessMapper confirmationOutboxDataAccessMapper) {
        this.confirmationOutboxJpaRepository = confirmationOutboxJpaRepository;
        this.confirmationOutboxDataAccessMapper = confirmationOutboxDataAccessMapper;
    }

    @Override
    public ConfirmationOutboxMessage save(ConfirmationOutboxMessage confirmationPaymentOutboxMessage) {
        return confirmationOutboxDataAccessMapper
                .confirmationOutboxEntityToConfirmationOutboxMessage(confirmationOutboxJpaRepository
                        .save(confirmationOutboxDataAccessMapper
                                .confirmationOutboxMessageToOutboxEntity(confirmationPaymentOutboxMessage)));
    }

    @Override
    public Optional<List<ConfirmationOutboxMessage>> findByTypeAndOutboxStatus(String sagaType, OutboxStatus outboxStatus) {
        return Optional.of(confirmationOutboxJpaRepository.findByTypeAndOutboxStatus(sagaType, outboxStatus)
                .orElseThrow(() -> new ConfirmationOutboxNotFoundException("Approval outbox object " +
                        "cannot be found for saga type " + sagaType))
                .stream()
                .map(confirmationOutboxDataAccessMapper::confirmationOutboxEntityToConfirmationOutboxMessage)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<ConfirmationOutboxMessage> findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(String sagaType,
                                                                            UUID sagaId,
                                                                            PaymentStatus paymentStatus,
                                                                            OutboxStatus outboxStatus) {
        return confirmationOutboxJpaRepository.findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(sagaType, sagaId,
                        paymentStatus, outboxStatus)
                .map(confirmationOutboxDataAccessMapper::confirmationOutboxEntityToConfirmationOutboxMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatus(String sagaType, OutboxStatus outboxStatus) {
        confirmationOutboxJpaRepository.deleteByTypeAndOutboxStatus(sagaType, outboxStatus);
    }
}
