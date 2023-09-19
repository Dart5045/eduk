package com.eduk.finance.service.messaging.mapper;

import com.eduk.domain.valueobject.FinanceConfirmationStatus;
import com.eduk.domain.valueobject.ProductId;
import com.eduk.finance.service.domain.dto.FinanceApprovalRequest;
import com.eduk.finance.service.domain.entity.Product;
import com.eduk.finance.service.domain.outbox.model.ConfirmationEventPayload;
import com.eduk.kafka.confirmation.avro.model.ConfirmationApprovalStatus;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalRequestAvroModel;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalResponseAvroModel;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FinanceMessagingDataMapper {

    public FinanceApprovalRequest
    financeApprovalRequestAvroModelToFinanceApproval(FinanceApprovalRequestAvroModel
                                                                   financeApprovalRequestAvroModel) {
        return FinanceApprovalRequest.builder()
                .id(financeApprovalRequestAvroModel.getId())
                .sagaId(financeApprovalRequestAvroModel.getSagaId())
                .financeId(financeApprovalRequestAvroModel.getFinanceId())
                .confirmationId(financeApprovalRequestAvroModel.getConfirmationId())
                .financeConfirmationStatus(FinanceConfirmationStatus.valueOf(financeApprovalRequestAvroModel
                        .getFinanceConfirmationStatus().name()))
                .products(financeApprovalRequestAvroModel.getProducts()
                        .stream().map(avroModel ->
                                Product.builder()
                                        .productId(new ProductId(UUID.fromString(avroModel.getId())))
                                        .quantity(avroModel.getQuantity())
                                        .build())
                        .collect(Collectors.toList()))
                .price(financeApprovalRequestAvroModel.getPrice())
                .createdAt(financeApprovalRequestAvroModel.getCreatedAt())
                .build();
    }

    public FinanceApprovalResponseAvroModel
    confirmationEventPayloadToFinanceApprovalResponseAvroModel(String sagaId, ConfirmationEventPayload confirmationEventPayload) {
        return FinanceApprovalResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setConfirmationId(confirmationEventPayload.getConfirmationId())
                .setFinanceId(confirmationEventPayload.getFinanceId())
                .setCreatedAt(confirmationEventPayload.getCreatedAt().toInstant())
                .setConfirmationApprovalStatus(ConfirmationApprovalStatus.valueOf(confirmationEventPayload.getConfirmationApprovalStatus()))
                .setFailureMessages(confirmationEventPayload.getFailureMessages())
                .build();
    }
}
