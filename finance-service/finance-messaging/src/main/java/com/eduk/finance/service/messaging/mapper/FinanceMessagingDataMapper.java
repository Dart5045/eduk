package com.eduk.finance.service.messaging.mapper;

import com.eduk.admission.service.domain.valueobject.ProductId;
import com.eduk.finance.service.domain.dto.FinanceApprovalRequest;
import com.eduk.finance.service.domain.entity.Product;
import com.eduk.finance.service.domain.event.ConfirmationApprovedEvent;
import com.eduk.finance.service.domain.event.ConfirmationRejectedEvent;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalRequestAvroModel;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalResponseAvroModel;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FinanceMessagingDataMapper {
    public FinanceApprovalResponseAvroModel
    confirmationApprovedEventToFinanceApprovalResponseAvroModel(ConfirmationApprovedEvent confirmationApprovedEvent) {
        return FinanceApprovalResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.randomUUID())
                .setConfirmationId(confirmationApprovedEvent.getConfirmationApproval().getConfirmationId().getValue())
                .setFinanceId(confirmationApprovedEvent.getFinanceId().getValue())
                .setCreatedAt(confirmationApprovedEvent.getCreatedAt().toInstant())
                /*.setConfirmationApprovalStatus(ConfirmationApprovalStatus.valueOf(confirmationApprovedEvent.
                        getConfirmationApproval().getApprovalStatus().name()))*/
                .setFailureMessages(confirmationApprovedEvent.getFailureMessages())
                .build();
    }

    public FinanceApprovalResponseAvroModel
    confirmationRejectedEventToFinanceApprovalResponseAvroModel(ConfirmationRejectedEvent confirmationRejectedEvent) {
        return FinanceApprovalResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.randomUUID())
                .setConfirmationId(confirmationRejectedEvent.getConfirmationApproval().getConfirmationId().getValue())
                .setFinanceId(confirmationRejectedEvent.getFinanceId().getValue())
                .setCreatedAt(confirmationRejectedEvent.getCreatedAt().toInstant())
                /*.setConfirmationApprovalStatus(ConfirmationApprovalStatus.valueOf(confirmationRejectedEvent.
                        getConfirmationApproval().getApprovalStatus().name()))*/
                .setFailureMessages(confirmationRejectedEvent.getFailureMessages())
                .build();
    }

    public FinanceApprovalRequest
    restaurantApprovalRequestAvroModelToFinanceApproval(FinanceApprovalRequestAvroModel
                                                                   restaurantApprovalRequestAvroModel) {
        return FinanceApprovalRequest.builder()
                .id(restaurantApprovalRequestAvroModel.getId().toString())
                .sagaId(restaurantApprovalRequestAvroModel.getSagaId().toString())
                .financeId(restaurantApprovalRequestAvroModel.getFinanceId().toString())
                .confirmationId(restaurantApprovalRequestAvroModel.getConfirmationId().toString())
                /*.financeConfirmationStatus(FinanceConfirmationStatus.valueOf(restaurantApprovalRequestAvroModel
                        .getFinanceConfirmationStatus().name()))*/
                .products(restaurantApprovalRequestAvroModel.getProducts()
                        .stream().map(avroModel ->
                                Product.builder()
                                        .productId(new ProductId(UUID.fromString(avroModel.getId())))
                                        .quantity(avroModel.getQuantity())
                                        .build())
                        .collect(Collectors.toList()))
                .price(restaurantApprovalRequestAvroModel.getPrice())
                .createdAt(restaurantApprovalRequestAvroModel.getCreatedAt())
                .build();
    }
}
