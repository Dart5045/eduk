package com.eduk.finance.service.dataaccess.finance.outbox.adapter;

import com.eduk.finance.service.dataaccess.finance.outbox.exception.ConfirmationOutboxNotFoundException;
import com.eduk.finance.service.dataaccess.finance.outbox.mapper.ConfirmationOutboxDataAccessMapper;
import com.eduk.finance.service.dataaccess.finance.outbox.repository.ConfirmationOutboxJpaRepository;
import com.eduk.finance.service.domain.outbox.model.ConfirmationOutboxMessage;
import com.eduk.finance.service.domain.ports.output.repository.ConfirmationOutboxRepository;
import com.eduk.outbox.OutboxStatus;
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
    public Optional<ConfirmationOutboxMessage> findByTypeAndSagaIdAndOutboxStatus(String type, UUID sagaId,
                                                                           OutboxStatus outboxStatus) {
        return confirmationOutboxJpaRepository.findByTypeAndSagaIdAndOutboxStatus(type, sagaId, outboxStatus)
                .map(confirmationOutboxDataAccessMapper::confirmationOutboxEntityToConfirmationOutboxMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus) {
        confirmationOutboxJpaRepository.deleteByTypeAndOutboxStatus(type, outboxStatus);
    }
}
