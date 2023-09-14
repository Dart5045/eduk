package com.eduk.admission.service.dataaccess.outbox.financeapproval.adapter;

import com.eduk.admission.service.dataaccess.outbox.financeapproval.exception.ApprovalOutboxNotFoundException;
import com.eduk.admission.service.dataaccess.outbox.financeapproval.mapper.ApprovalOutboxDataAccessMapper;
import com.eduk.admission.service.dataaccess.outbox.financeapproval.repository.ApprovalOutboxJpaRepository;
import com.eduk.admission.service.domain.outbox.model.approval.ConfirmationApprovalOutboxMessage;
import com.eduk.admission.service.domain.ports.output.repository.ApprovalOutboxRepository;
import com.eduk.outbox.OutboxStatus;
import com.eduk.saga.SagaStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApprovalOutboxRepositoryImpl implements ApprovalOutboxRepository {

    private final ApprovalOutboxJpaRepository approvalOutboxJpaRepository;
    private final ApprovalOutboxDataAccessMapper approvalOutboxDataAccessMapper;

    public ApprovalOutboxRepositoryImpl(ApprovalOutboxJpaRepository approvalOutboxJpaRepository,
                                        ApprovalOutboxDataAccessMapper approvalOutboxDataAccessMapper) {
        this.approvalOutboxJpaRepository = approvalOutboxJpaRepository;
        this.approvalOutboxDataAccessMapper = approvalOutboxDataAccessMapper;
    }

    @Override
    public ConfirmationApprovalOutboxMessage save(ConfirmationApprovalOutboxMessage confirmationApprovalOutboxMessage) {
        return approvalOutboxDataAccessMapper
                .approvalOutboxEntityToConfirmationApprovalOutboxMessage(approvalOutboxJpaRepository
                        .save(approvalOutboxDataAccessMapper
                                .confirmationCreatedOutboxMessageToOutboxEntity(confirmationApprovalOutboxMessage)));
    }

    @Override
    public Optional<List<ConfirmationApprovalOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String sagaType,
                                                                                       OutboxStatus outboxStatus,
                                                                       SagaStatus... sagaStatus) {
        return Optional.of(approvalOutboxJpaRepository.findByTypeAndOutboxStatusAndSagaStatusIn(sagaType, outboxStatus,
                Arrays.asList(sagaStatus))
                .orElseThrow(() -> new ApprovalOutboxNotFoundException("Approval outbox object " +
                        "could be found for saga type " + sagaType))
                .stream()
                .map(approvalOutboxDataAccessMapper::approvalOutboxEntityToConfirmationApprovalOutboxMessage)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<ConfirmationApprovalOutboxMessage> findByTypeAndSagaIdAndSagaStatus(String type,
                                                                                 UUID sagaId,
                                                                                 SagaStatus... sagaStatus) {
        return approvalOutboxJpaRepository
                .findByTypeAndSagaIdAndSagaStatusIn(type, sagaId,
                        Arrays.asList(sagaStatus))
                .map(approvalOutboxDataAccessMapper::approvalOutboxEntityToConfirmationApprovalOutboxMessage);

    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        approvalOutboxJpaRepository.deleteByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus,
                Arrays.asList(sagaStatus));
    }
}
