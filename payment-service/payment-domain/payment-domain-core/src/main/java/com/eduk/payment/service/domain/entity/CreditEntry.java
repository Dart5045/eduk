package com.eduk.payment.service.domain.entity;

import com.eduk.admission.service.domain.entity.BaseEntity;
import com.eduk.admission.service.domain.valueobject.ApplicationId;
import com.eduk.admission.service.domain.valueobject.Money;
import com.eduk.payment.service.domain.valueobject.CreditEntryId;

public class CreditEntry extends BaseEntity<CreditEntryId> {
    private final ApplicationId applicationId;
    private Money totalCreditAmount;

    public void addCreditAmount(Money amount){
        totalCreditAmount = totalCreditAmount.add(amount);
    }
    public void subtractCreditAmount(Money amount){
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }

    private CreditEntry(Builder builder) {
        super.setId(builder.creditEntryId);
        applicationId = builder.applicationId;
        totalCreditAmount = builder.totalCreditAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ApplicationId getApplicationId() {
        return applicationId;
    }

    public Money getTotalCreditAmount() {
        return totalCreditAmount;
    }


    public static final class Builder {
        private CreditEntryId creditEntryId;
        private ApplicationId applicationId;
        private Money totalCreditAmount;

        private Builder() {
        }

        public Builder creditEntryId(CreditEntryId val) {
            creditEntryId = val;
            return this;
        }

        public Builder applicationId(ApplicationId val) {
            applicationId = val;
            return this;
        }

        public Builder totalCreditAmount(Money val) {
            totalCreditAmount = val;
            return this;
        }

        public CreditEntry build() {
            return new CreditEntry(this);
        }
    }
}
