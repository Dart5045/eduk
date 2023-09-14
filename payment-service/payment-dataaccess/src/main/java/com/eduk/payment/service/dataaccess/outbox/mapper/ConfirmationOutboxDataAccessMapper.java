package com.eduk.payment.service.dataaccess.outbox.mapper;

import com.eduk.paymenet.service.domain.outbox.model.ConfirmationOutboxMessage;
import com.eduk.payment.service.dataaccess.outbox.entity.ConfirmationOutboxEntity;
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
                .paymentStatus(confirmationOutboxMessage.getPaymentStatus())
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
                .paymentStatus(paymentOutboxEntity.getPaymentStatus())
                .version(paymentOutboxEntity.getVersion())
                .build();
    }

}
