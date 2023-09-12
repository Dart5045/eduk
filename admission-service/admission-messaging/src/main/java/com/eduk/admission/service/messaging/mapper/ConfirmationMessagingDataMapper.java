package com.eduk.admission.service.messaging.mapper;

import com.eduk.admission.service.domain.dto.message.FinanceApprovalResponse;
import com.eduk.admission.service.domain.dto.message.StudentModel;
import com.eduk.admission.service.domain.Confirmation;
import com.eduk.admission.service.domain.event.ConfirmationCancelledEvent;
import com.eduk.admission.service.domain.event.ConfirmationCreatedEvent;
import com.eduk.admission.service.domain.event.ConfirmationPaidEvent;
import com.eduk.kafka.confirmation.avro.model.*;
import com.eduk.admission.service.domain.dto.message.PaymentResponse;
import com.eduk.kafka.student.avro.model.StudentAvroModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConfirmationMessagingDataMapper {

    public PaymentRequestAvroModel confirmationCreatedEventToPaymentRequestAvroModel(ConfirmationCreatedEvent confirmationCreatedEvent) {
        Confirmation confirmation = confirmationCreatedEvent.getConfirmation();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.randomUUID())
                .setApplicationId(confirmation.getApplicationId().getValue())
                .setConfirmationId(confirmation.getId().getValue())
                .setPrice(confirmation.getAmount().getAmount())
                .setCreatedAt(confirmationCreatedEvent.getCreatedAt().toInstant())
                .setPaymentConfirmationStatus(PaymentConfirmationStatus.PENDING)
                .build();
    }

    public PaymentRequestAvroModel confirmationCancelledEventToPaymentRequestAvroModel(ConfirmationCancelledEvent confirmationCancelledEvent) {
        Confirmation confirmation = confirmationCancelledEvent.getConfirmation();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                //.setSagaId("")
                .setApplicationId(confirmation.getApplicationId().getValue())
                .setConfirmationId(confirmation.getId().getValue())
                .setPrice(confirmation.getAmount().getAmount())
                .setCreatedAt(confirmationCancelledEvent.getCreatedAt().toInstant())
                .setPaymentConfirmationStatus(PaymentConfirmationStatus.CANCELLED)
                .build();
    }

    public FinanceApprovalRequestAvroModel
    confirmationPaidEventToFinanceApprovalRequestAvroModel(ConfirmationPaidEvent confirmationPaidEvent) {
        Confirmation confirmation = confirmationPaidEvent.getConfirmation();
        return FinanceApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                //.setSagaId("")
                .setConfirmationId(confirmation.getId().getValue())
                //.setFinanceId(confirmation.getFinanceId().getValue())
                .setConfirmationId(confirmation.getId().getValue())
                .setFinanceConfirmationStatus(FinanceConfirmationStatus
                        .valueOf(confirmation.getConfirmationStatus().name()))
                /*.setProducts(confirmation.getItems().stream().map(confirmationItem ->
                        com.food.confirmationing.system.kafka.confirmation.avro.model.Product.newBuilder()
                                .setId(confirmationItem.getProduct().getId().getValue().toString())
                                .setQuantity(confirmationItem.getQuantity())
                                .build()).collect(Collectors.toList()))*/
                .setPrice(confirmation.getAmount().getAmount())
                .setCreatedAt(confirmationPaidEvent.getCreatedAt().toInstant())
                .setFinanceConfirmationStatus(FinanceConfirmationStatus.PAID)
                .build();
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel
                                                                             paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId().toString())
                .sagaId(paymentResponseAvroModel.getSagaId().toString())
                .paymentId(paymentResponseAvroModel.getPaymentId().toString())
                .applicationId(paymentResponseAvroModel.getApplicationId().toString())
                .confirmationId(paymentResponseAvroModel.getConfirmationId().toString())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                /*.paymentStatus(PaymentStatus.valueOf(
                        paymentResponseAvroModel.getPaymentStatus().name()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())*/
                .build();
    }

    public FinanceApprovalResponse
    approvalResponseAvroModelToApprovalResponse(FinanceApprovalResponseAvroModel
                                                        financeApprovalResponseAvroModel) {
        return FinanceApprovalResponse.builder()
                .id(financeApprovalResponseAvroModel.getId().toString())
                .sagaId(financeApprovalResponseAvroModel.getSagaId().toString())
                .financeId(financeApprovalResponseAvroModel.getFinanceId().toString())
                .confirmationId(financeApprovalResponseAvroModel.getConfirmationId().toString())
                .createdAt(financeApprovalResponseAvroModel.getCreatedAt())
                /*.financeApprovalStatus(ConfirmationApprovalStatus.valueOf(
                        financeApprovalResponseAvroModel.getConfirmationApprovalStatus().name()))
                */
                .failureMessages(financeApprovalResponseAvroModel.getFailureMessages())
                .build();
    }
    public StudentModel studentAvroModeltoStudentModel(StudentAvroModel studentAvroModel) {
        return StudentModel.builder()
                .id(studentAvroModel.getId().toString())
                .email(studentAvroModel.getEmail())
                .firstName(studentAvroModel.getFirstName())
                .lastName(studentAvroModel.getLastName())
                .build();
    }
}
