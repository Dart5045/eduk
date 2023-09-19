package com.eduk.payment.service.messaging.mapper;

import com.eduk.kafka.confirmation.avro.model.PaymentRequestAvroModel;
import com.eduk.kafka.confirmation.avro.model.PaymentResponseAvroModel;
import com.eduk.kafka.confirmation.avro.model.PaymentStatus;
import com.eduk.paymenet.service.domain.dto.PaymentRequest;
import com.eduk.payment.service.domain.event.PaymentCancelledEvent;
import com.eduk.payment.service.domain.event.PaymentCompletedEvent;
import com.eduk.payment.service.domain.event.PaymentFailedEvent;
import com.eduk.payment.service.domain.valueobject.PaymentConfirmationStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {

    public PaymentResponseAvroModel
    paymentCompletedEventToPaymentResponseAvroModel(PaymentCompletedEvent paymentCompletedEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(UUID.randomUUID().toString())
                .setPaymentId(paymentCompletedEvent.getPayment().getId().getValue().toString())
                .setApplicationId(paymentCompletedEvent.getPayment().getApplicationId().getValue().toString())
                .setConfirmationId(paymentCompletedEvent.getPayment().getConfirmationId().getValue().toString())
                .setPrice(paymentCompletedEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentCompletedEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCompletedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCompletedEvent.getFailureMessages())
                .build();
    }

    public PaymentResponseAvroModel
    paymentCancelledEventToPaymentResponseAvroModel(PaymentCancelledEvent paymentCancelledEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(UUID.randomUUID().toString())
                .setPaymentId(paymentCancelledEvent.getPayment().getId().getValue().toString())
                .setApplicationId(paymentCancelledEvent.getPayment().getApplicationId().getValue().toString())
                .setConfirmationId(paymentCancelledEvent.getPayment().getConfirmationId().getValue().toString())
                .setPrice(paymentCancelledEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentCancelledEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCancelledEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCancelledEvent.getFailureMessages())
                .build();
    }

    public PaymentResponseAvroModel
    paymentFailedEventToPaymentResponseAvroModel(PaymentFailedEvent paymentFailedEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(UUID.randomUUID().toString())
                .setPaymentId(paymentFailedEvent.getPayment().getId().getValue().toString())
                .setApplicationId(paymentFailedEvent.getPayment().getApplicationId().getValue().toString())
                .setConfirmationId(paymentFailedEvent.getPayment().getConfirmationId().getValue().toString())
                .setPrice(paymentFailedEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentFailedEvent.getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentFailedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentFailedEvent.getFailureMessages())
                .build();
    }

    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId().toString())
                .sagaId(paymentRequestAvroModel.getSagaId().toString())
                .applicationId(paymentRequestAvroModel.getApplicationId().toString())
                .confirmationId(paymentRequestAvroModel.getConfirmationId().toString())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentConfirmationStatus(PaymentConfirmationStatus.valueOf(paymentRequestAvroModel.getPaymentConfirmationStatus().name()))
                .build();
    }
}
