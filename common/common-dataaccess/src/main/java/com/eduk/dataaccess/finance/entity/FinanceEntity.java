package com.eduk.dataaccess.finance.entity;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FinanceEntityId.class)
@Table(name = "confirmation_finance_m_view", schema = "finance")
@Entity
public class FinanceEntity {

    @Id
    private UUID financeId;
    @Id
    private UUID productId;
    private String financeName;
    private Boolean financeActive;
    private String productName;
    private BigDecimal productPrice;
    private Boolean productAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinanceEntity that = (FinanceEntity) o;
        return financeId.equals(that.financeId) && productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(financeId, productId);
    }
}
