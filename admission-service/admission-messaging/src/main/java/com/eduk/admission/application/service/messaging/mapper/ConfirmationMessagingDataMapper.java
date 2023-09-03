package com.eduk.admission.application.service.messaging.mapper;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.event.ConfirmationCancelledEvent;
import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.kafka.confirmation.avro.model.PaymentOrderStatus;
import com.eduk.kafka.confirmation.avro.model.PaymentRequestAvroModel;
import com.eduk.kafka.confirmation.avro.model.PaymentResponseAvroModel;
import com.eduk.service.domain.dto.message.PaymentResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConfirmationMessagingDataMapper {
    public PaymentRequestAvroModel confirmationCreatedEventToPaymentRequestAvroModel(
            ConfirmationCreatedEvent createdEvent
    ){
        Confirmation confirmation = createdEvent.getConfirmation();
        return PaymentRequestAvroModel
                .newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.randomUUID())
                .setApplicationId(confirmation.getApplicationId().getValue())
                .setConfirmationId(confirmation.getId().getValue())
                .setPrice(confirmation.getAmount().getAmount())
                .setCreatedAt(createdEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }

    public PaymentRequestAvroModel confirmationCancelledEventToPaymentRequestAvroModel(
            ConfirmationCancelledEvent cancelledEvent
    ){
        Confirmation confirmation = cancelledEvent.getConfirmation();
        return PaymentRequestAvroModel
                .newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.randomUUID())
                .setApplicationId(confirmation.getApplicationId().getValue())
                .setConfirmationId(confirmation.getId().getValue())
                .setPrice(confirmation.getAmount().getAmount())
                .setCreatedAt(cancelledEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                .build();
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(
            PaymentResponseAvroModel paymentResponseAvroModel
    ){
        return PaymentResponse
                .builder()
                .sagaId(paymentResponseAvroModel.getSagaId().toString())
                .paymentId(paymentResponseAvroModel.getPaymentId().toString())
                .applicationId(paymentResponseAvroModel.getApplicationId().toString())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                //falta payment status
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }
}
