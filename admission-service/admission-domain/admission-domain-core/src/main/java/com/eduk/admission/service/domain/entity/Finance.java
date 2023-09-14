package com.eduk.admission.service.domain.entity;

import com.eduk.domain.entity.AggregateRoot;
import com.eduk.domain.valueobject.FinanceId;

import java.util.List;

public class Finance extends AggregateRoot<FinanceId> {
    private final List<Product> products;
    private boolean active;

    private Finance(Builder builder) {
        super.setId(builder.financeId);
        products = builder.products;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private FinanceId financeId;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public Builder financeId(FinanceId val) {
            financeId = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Finance build() {
            return new Finance(this);
        }
    }
}
