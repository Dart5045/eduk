package com.eduk.finance.service.domain.mapper;

import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.domain.valueobject.Money;
import com.eduk.finance.service.domain.entity.ConfirmationDetail;
import com.eduk.finance.service.domain.entity.Finance;
import com.eduk.finance.service.domain.entity.Product;
import com.eduk.domain.valueobject.ConfirmationStatus;
import com.eduk.domain.valueobject.FinanceId;
import com.eduk.finance.service.domain.dto.FinanceApprovalRequest;
import com.eduk.finance.service.domain.event.ConfirmationApprovalEvent;
import com.eduk.finance.service.domain.outbox.model.ConfirmationEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FinanceDataMapper {
    public Finance financeApprovalRequestToFinance(FinanceApprovalRequest
                                                                    financeApprovalRequest) {
        return Finance.builder()
                .financeId(new FinanceId(UUID.fromString(financeApprovalRequest.getFinanceId())))
                .confirmationDetail(ConfirmationDetail.builder()
                        .confirmationId(new ConfirmationId(UUID.fromString(financeApprovalRequest.getConfirmationId())))
                        .products(financeApprovalRequest.getProducts().stream().map(
                                        product -> Product.builder()
                                                .productId(product.getId())
                                                .quantity(product.getQuantity())
                                                .build())
                                .collect(Collectors.toList()))
                        .totalAmount(new Money(financeApprovalRequest.getPrice()))
                        .confirmationStatus(ConfirmationStatus.valueOf(financeApprovalRequest.getFinanceConfirmationStatus().name()))
                        .build())
                .build();
    }

    public ConfirmationEventPayload
    confirmationApprovalEventToConfirmationEventPayload(ConfirmationApprovalEvent confirmationApprovalEvent) {
        return ConfirmationEventPayload.builder()
                .confirmationId(confirmationApprovalEvent.getConfirmationApproval().getConfirmationId().getValue().toString())
                .financeId(confirmationApprovalEvent.getFinanceId().getValue().toString())
                .confirmationApprovalStatus(confirmationApprovalEvent.getConfirmationApproval().getApprovalStatus().name())
                .createdAt(confirmationApprovalEvent.getCreatedAt())
                .failureMessages(confirmationApprovalEvent.getFailureMessages())
                .build();
    }
}
