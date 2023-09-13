package com.eduk.finance.service.dataaccess.finance.outbox.mapper;

import com.eduk.finance.service.dataaccess.finance.outbox.entity.ConfirmationOutboxEntity;
import com.eduk.finance.service.domain.outbox.model.ConfirmationOutboxMessage;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationOutboxDataAccessMapper {

    public ConfirmationOutboxEntity confirmationOutboxMessageToOutboxEntity(ConfirmationOutboxMessage confirmationOutboxMessage) {
        return ConfirmationOutboxEntity.builder()
                .id(confirmationOutboxMessage.getId())
                .sagaId(confirmationOutboxMessage.getSagaId())
                .createdAt(confirmationOutboxMessage.getCreatedAt())
                .type(confirmationOutboxMessage.getType())
                .payload(confirmationOutboxMessage.getPayload())
                .outboxStatus(confirmationOutboxMessage.getOutboxStatus())
                .approvalStatus(confirmationOutboxMessage.getApprovalStatus())
                .version(confirmationOutboxMessage.getVersion())
                .build();
    }

    public ConfirmationOutboxMessage confirmationOutboxEntityToConfirmationOutboxMessage(ConfirmationOutboxEntity paymentOutboxEntity) {
        return ConfirmationOutboxMessage.builder()
                .id(paymentOutboxEntity.getId())
                .sagaId(paymentOutboxEntity.getSagaId())
                .createdAt(paymentOutboxEntity.getCreatedAt())
                .type(paymentOutboxEntity.getType())
                .payload(paymentOutboxEntity.getPayload())
                .outboxStatus(paymentOutboxEntity.getOutboxStatus())
                .approvalStatus(paymentOutboxEntity.getApprovalStatus())
                .version(paymentOutboxEntity.getVersion())
                .build();
    }

}
