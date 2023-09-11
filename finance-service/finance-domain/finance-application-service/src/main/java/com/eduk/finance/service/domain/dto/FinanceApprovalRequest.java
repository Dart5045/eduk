package com.eduk.finance.service.domain.dto;
import com.eduk.finance.service.domain.entity.Product;
import com.eduk.domain.valueobject.FinanceOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FinanceApprovalRequest {
    private String id;
    private String sagaId;
    private String financeId;
    private String confirmationId;
    private FinanceOrderStatus financeConfirmationStatus;
    private java.util.List<Product> products;
    private java.math.BigDecimal price;
    private java.time.Instant createdAt;
}
