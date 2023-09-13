package com.eduk.dataaccess.finance.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceEntityId implements Serializable {

    private UUID financeId;
    private UUID productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinanceEntityId that = (FinanceEntityId) o;
        return financeId.equals(that.financeId) && productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(financeId, productId);
    }
}
