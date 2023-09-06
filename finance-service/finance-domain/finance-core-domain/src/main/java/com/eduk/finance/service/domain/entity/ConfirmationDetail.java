package com.eduk.finance.service.domain.entity;


import com.eduk.domain.entity.BaseEntity;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.domain.valueobject.ConfirmationStatus;
import com.eduk.domain.valueobject.Money;

import java.util.List;

public class ConfirmationDetail extends BaseEntity<ConfirmationId> {
    private ConfirmationStatus confirmationStatus;
    private Money totalAmount;
    private final List<Product> products;

    private ConfirmationDetail(Builder builder) {
        setId(builder.confirmationId);
        confirmationStatus = builder.confirmationStatus;
        totalAmount = builder.totalAmount;
        products = builder.products;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ConfirmationStatus getConfirmationStatus() {
        return confirmationStatus;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public static final class Builder {
        private ConfirmationId confirmationId;
        private ConfirmationStatus confirmationStatus;
        private Money totalAmount;
        private List<Product> products;

        private Builder() {
        }

        public Builder confirmationId(ConfirmationId val) {
            confirmationId = val;
            return this;
        }

        public Builder confirmationStatus(ConfirmationStatus val) {
            confirmationStatus = val;
            return this;
        }

        public Builder totalAmount(Money val) {
            totalAmount = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public ConfirmationDetail build() {
            return new ConfirmationDetail(this);
        }
    }
}
