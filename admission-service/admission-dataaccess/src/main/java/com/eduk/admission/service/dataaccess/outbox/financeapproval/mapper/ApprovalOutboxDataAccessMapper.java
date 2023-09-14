package com.eduk.admission.service.dataaccess.outbox.financeapproval.mapper;

import com.eduk.admission.service.dataaccess.outbox.financeapproval.entity.ApprovalOutboxEntity;
import com.eduk.admission.service.domain.outbox.model.approval.ConfirmationApprovalOutboxMessage;
import org.springframework.stereotype.Component;

@Component
public class ApprovalOutboxDataAccessMapper {

    public ApprovalOutboxEntity confirmationCreatedOutboxMessageToOutboxEntity(ConfirmationApprovalOutboxMessage
                                                                                confirmationApprovalOutboxMessage) {
        return ApprovalOutboxEntity.builder()
                .id(confirmationApprovalOutboxMessage.getId())
                .sagaId(confirmationApprovalOutboxMessage.getSagaId())
                .createdAt(confirmationApprovalOutboxMessage.getCreatedAt())
                .type(confirmationApprovalOutboxMessage.getType())
                .payload(confirmationApprovalOutboxMessage.getPayload())
                .confirmationStatus(confirmationApprovalOutboxMessage.getConfirmationStatus())
                .sagaStatus(confirmationApprovalOutboxMessage.getSagaStatus())
                .outboxStatus(confirmationApprovalOutboxMessage.getOutboxStatus())
                .version(confirmationApprovalOutboxMessage.getVersion())
                .build();
    }

    public ConfirmationApprovalOutboxMessage approvalOutboxEntityToConfirmationApprovalOutboxMessage(ApprovalOutboxEntity
                                                                                               approvalOutboxEntity) {
        return ConfirmationApprovalOutboxMessage.builder()
                .id(approvalOutboxEntity.getId())
                .sagaId(approvalOutboxEntity.getSagaId())
                .createdAt(approvalOutboxEntity.getCreatedAt())
                .type(approvalOutboxEntity.getType())
                .payload(approvalOutboxEntity.getPayload())
                .confirmationStatus(approvalOutboxEntity.getConfirmationStatus())
                .sagaStatus(approvalOutboxEntity.getSagaStatus())
                .outboxStatus(approvalOutboxEntity.getOutboxStatus())
                .version(approvalOutboxEntity.getVersion())
                .build();
    }

}
