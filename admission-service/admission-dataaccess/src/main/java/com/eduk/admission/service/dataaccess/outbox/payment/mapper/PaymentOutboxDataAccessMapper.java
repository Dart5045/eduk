package com.eduk.admission.service.dataaccess.outbox.payment.mapper;

import com.eduk.admission.service.dataaccess.outbox.payment.entity.PaymentOutboxEntity;
import com.eduk.admission.service.domain.outbox.model.payment.ConfirmationPaymentOutboxMessage;
import org.springframework.stereotype.Component;

@Component
public class PaymentOutboxDataAccessMapper {

    public PaymentOutboxEntity confirmationPaymentOutboxMessageToOutboxEntity(ConfirmationPaymentOutboxMessage
                                                                               confirmationPaymentOutboxMessage) {
        return PaymentOutboxEntity.builder()
                .id(confirmationPaymentOutboxMessage.getId())
                .sagaId(confirmationPaymentOutboxMessage.getSagaId())
                .createdAt(confirmationPaymentOutboxMessage.getCreatedAt())
                .type(confirmationPaymentOutboxMessage.getType())
                .payload(confirmationPaymentOutboxMessage.getPayload())
                .confirmationStatus(confirmationPaymentOutboxMessage.getConfirmationStatus())
                .sagaStatus(confirmationPaymentOutboxMessage.getSagaStatus())
                .outboxStatus(confirmationPaymentOutboxMessage.getOutboxStatus())
                .version(confirmationPaymentOutboxMessage.getVersion())
                .build();
    }

    public ConfirmationPaymentOutboxMessage paymentOutboxEntityToConfirmationPaymentOutboxMessage(PaymentOutboxEntity
                                                                               paymentOutboxEntity) {
        return ConfirmationPaymentOutboxMessage.builder()
                .id(paymentOutboxEntity.getId())
                .sagaId(paymentOutboxEntity.getSagaId())
                .createdAt(paymentOutboxEntity.getCreatedAt())
                .type(paymentOutboxEntity.getType())
                .payload(paymentOutboxEntity.getPayload())
                .confirmationStatus(paymentOutboxEntity.getConfirmationStatus())
                .sagaStatus(paymentOutboxEntity.getSagaStatus())
                .outboxStatus(paymentOutboxEntity.getOutboxStatus())
                .version(paymentOutboxEntity.getVersion())
                .build();
    }

}
