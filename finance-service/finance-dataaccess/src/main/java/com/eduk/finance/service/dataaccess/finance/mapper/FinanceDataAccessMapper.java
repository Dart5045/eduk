package com.eduk.finance.service.dataaccess.finance.mapper;

import com.eduk.dataaccess.finance.entity.FinanceEntity;
import com.eduk.dataaccess.finance.exception.FinanceDataAccessException;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.domain.valueobject.FinanceId;
import com.eduk.domain.valueobject.Money;
import com.eduk.domain.valueobject.ProductId;
import com.eduk.finance.service.dataaccess.finance.entity.ConfirmationApprovalEntity;
import com.eduk.finance.service.domain.entity.ConfirmationApproval;
import com.eduk.finance.service.domain.entity.ConfirmationDetail;
import com.eduk.finance.service.domain.entity.Finance;
import com.eduk.finance.service.domain.entity.Product;
import com.eduk.finance.service.domain.valueobject.ConfirmationApprovalId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FinanceDataAccessMapper {

    public List<UUID> financeToFinanceProducts(Finance finance) {
        return finance.getConfirmationDetail().getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Finance financeEntityToFinance(List<FinanceEntity> financeEntities) {
        FinanceEntity financeEntity =
                financeEntities.stream().findFirst().orElseThrow(() ->
                        new FinanceDataAccessException("No finances found!"));

        List<Product> financeProducts = financeEntities.stream().map(entity ->
                        Product.builder()
                                .productId(new ProductId(entity.getProductId()))
                                .name(entity.getProductName())
                                .price(new Money(entity.getProductPrice()))
                                .available(entity.getProductAvailable())
                                .build())
                .collect(Collectors.toList());

        return Finance.builder()
                .financeId(new FinanceId(financeEntity.getFinanceId()))
                .confirmationDetail(ConfirmationDetail.builder()
                        .products(financeProducts)
                        .build())
                .active(financeEntity.getFinanceActive())
                .build();
    }

    public ConfirmationApprovalEntity confirmationApprovalToConfirmationApprovalEntity(ConfirmationApproval confirmationApproval) {
        return ConfirmationApprovalEntity.builder()
                .id(confirmationApproval.getId().getValue())
                .financeId(confirmationApproval.getFinanceId().getValue())
                .confirmationId(confirmationApproval.getConfirmationId().getValue())
                .status(confirmationApproval.getApprovalStatus())
                .build();
    }

    public ConfirmationApproval confirmationApprovalEntityToConfirmationApproval(ConfirmationApprovalEntity confirmationApprovalEntity) {
        return ConfirmationApproval.builder()
                .confirmationApprovalId(new ConfirmationApprovalId(confirmationApprovalEntity.getId()))
                .financeId(new FinanceId(confirmationApprovalEntity.getFinanceId()))
                .confirmationId(new ConfirmationId(confirmationApprovalEntity.getConfirmationId()))
                .approvalStatus(confirmationApprovalEntity.getStatus())
                .build();
    }

}
