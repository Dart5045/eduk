package com.eduk.payment.service.domain.entity;

import com.eduk.domain.entity.BaseEntity;
import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.domain.valueobject.Money;
import com.eduk.payment.service.domain.valueobject.CreditHistoryId;
import com.eduk.payment.service.domain.valueobject.TransactionType;

public class CreditHistory extends BaseEntity<CreditHistoryId> {
    private final ApplicationId applicationId;
    private final Money amount;
    private final TransactionType transactionType;

    private CreditHistory(Builder builder) {
        super.setId(builder.CreditHistoryId);
        applicationId = builder.applicationId;
        amount = builder.amount;
        transactionType = builder.transactionType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ApplicationId getApplicationId() {
        return applicationId;
    }

    public Money getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }


    public static final class Builder {
        private CreditHistoryId CreditHistoryId;
        private ApplicationId applicationId;
        private Money amount;
        private TransactionType transactionType;

        private Builder() {
        }

        public Builder creditHistoryId(CreditHistoryId val) {
            CreditHistoryId = val;
            return this;
        }

        public Builder applicationId(ApplicationId val) {
            applicationId = val;
            return this;
        }

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder transactionType(TransactionType val) {
            transactionType = val;
            return this;
        }

        public CreditHistory build() {
            return new CreditHistory(this);
        }
    }
}
