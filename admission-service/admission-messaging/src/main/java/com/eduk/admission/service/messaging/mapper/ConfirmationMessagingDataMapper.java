package com.eduk.admission.service.messaging.mapper;

import com.eduk.admission.service.domain.dto.message.FinanceApprovalResponse;
import com.eduk.admission.service.domain.dto.message.StudentModel;
import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.event.ConfirmationCancelledEvent;
import com.eduk.admission.service.domain.event.ConfirmationCreatedEvent;
import com.eduk.admission.service.domain.event.ConfirmationPaidEvent;
import com.eduk.domain.event.payload.ConfirmationApprovalEventPayload;
import com.eduk.domain.event.payload.ConfirmationPaymentEventPayload;
import com.eduk.kafka.confirmation.avro.model.*;
import com.eduk.admission.service.domain.dto.message.PaymentResponse;
import com.eduk.kafka.student.avro.model.StudentAvroModel;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConfirmationMessagingDataMapper {

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel
                                                                             paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId())
                .sagaId(paymentResponseAvroModel.getSagaId())
                .paymentId(paymentResponseAvroModel.getPaymentId())
                .applicationId(paymentResponseAvroModel.getApplicationId())
                .confirmationId(paymentResponseAvroModel.getConfirmationId())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(com.eduk.domain.valueobject.PaymentStatus.valueOf(
                        paymentResponseAvroModel.getPaymentStatus().name()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public FinanceApprovalResponse
    approvalResponseAvroModelToApprovalResponse(FinanceApprovalResponseAvroModel
                                                        financeApprovalResponseAvroModel) {
        return FinanceApprovalResponse.builder()
                .id(financeApprovalResponseAvroModel.getId())
                .sagaId(financeApprovalResponseAvroModel.getSagaId())
                .financeId(financeApprovalResponseAvroModel.getFinanceId())
                .confirmationId(financeApprovalResponseAvroModel.getConfirmationId())
                .createdAt(financeApprovalResponseAvroModel.getCreatedAt())
                .confirmationApprovalStatus(com.eduk.domain.valueobject.ConfirmationApprovalStatus.valueOf(
                        financeApprovalResponseAvroModel.getConfirmationApprovalStatus().name()))
                .failureMessages(financeApprovalResponseAvroModel.getFailureMessages())
                .build();
    }

    public PaymentRequestAvroModel confirmationPaymentEventToPaymentRequestAvroModel(String sagaId, ConfirmationPaymentEventPayload
            confirmationPaymentEventPayload) {
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setApplicationId(confirmationPaymentEventPayload.getApplicationId())
                .setConfirmationId(confirmationPaymentEventPayload.getConfirmationId())
                .setPrice(confirmationPaymentEventPayload.getPrice())
                .setCreatedAt(confirmationPaymentEventPayload.getCreatedAt().toInstant())
                .setPaymentConfirmationStatus(PaymentConfirmationStatus.valueOf(confirmationPaymentEventPayload.getPaymentConfirmationStatus()))
                .build();
    }

    public FinanceApprovalRequestAvroModel
    confirmationApprovalEventToFinanceApprovalRequestAvroModel(String sagaId, ConfirmationApprovalEventPayload
            confirmationApprovalEventPayload) {
        return FinanceApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setConfirmationId(confirmationApprovalEventPayload.getConfirmationId())
                .setFinanceId(confirmationApprovalEventPayload.getFinanceId())
                .setFinanceConfirmationStatus(FinanceConfirmationStatus
                        .valueOf(confirmationApprovalEventPayload.getFinanceConfirmationStatus()))
                .setProducts(confirmationApprovalEventPayload.getProducts().stream().map(confirmationApprovalEventProduct ->
                        com.eduk.kafka.confirmation.avro.model.Product.newBuilder()
                                .setId(confirmationApprovalEventProduct.getId())
                                .setQuantity(confirmationApprovalEventProduct.getQuantity())
                                .build()).collect(Collectors.toList()))
                .setPrice(confirmationApprovalEventPayload.getPrice())
                .setCreatedAt(confirmationApprovalEventPayload.getCreatedAt().toInstant())
                .build();
    }

    public StudentModel studentAvroModeltoStudentModel(StudentAvroModel studentAvroModel) {
        return StudentModel.builder()
                .id(studentAvroModel.getId())
                .email(studentAvroModel.getEmail())
                .firstName(studentAvroModel.getFirstName())
                .lastName(studentAvroModel.getLastName())
                .build();
    }
}
