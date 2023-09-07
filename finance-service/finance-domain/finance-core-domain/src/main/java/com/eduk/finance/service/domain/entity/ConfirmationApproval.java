package com.eduk.finance.service.domain.entity;

import com.eduk.admission.service.domain.entity.BaseEntity;
import com.eduk.admission.service.domain.valueobject.ConfirmationApprovalStatus;
import com.eduk.admission.service.domain.valueobject.ConfirmationId;
import com.eduk.admission.service.domain.valueobject.FinanceId;
import com.eduk.finance.service.domain.valueobject.ConfirmationApprovalId;

public class ConfirmationApproval extends BaseEntity<ConfirmationApprovalId> {
    private final FinanceId financeId;
    private final ConfirmationId confirmationId;
    private final ConfirmationApprovalStatus approvalStatus;

    private ConfirmationApproval(Builder builder) {
        setId(builder.confirmationApprovalId);
        financeId = builder.financeId;
        confirmationId = builder.confirmationId;
        approvalStatus = builder.approvalStatus;
    }

    public static Builder builder() {
        return new Builder();
    }


    public FinanceId getFinanceId() {
        return financeId;
    }

    public ConfirmationId getConfirmationId() {
        return confirmationId;
    }

    public ConfirmationApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public static final class Builder {
        private ConfirmationApprovalId confirmationApprovalId;
        private FinanceId financeId;
        private ConfirmationId confirmationId;
        private ConfirmationApprovalStatus approvalStatus;

        private Builder() {
        }

        public Builder confirmationApprovalId(ConfirmationApprovalId val) {
            confirmationApprovalId = val;
            return this;
        }

        public Builder financeId(FinanceId val) {
            financeId = val;
            return this;
        }

        public Builder confirmationId(ConfirmationId val) {
            confirmationId = val;
            return this;
        }

        public Builder approvalStatus(ConfirmationApprovalStatus val) {
            approvalStatus = val;
            return this;
        }

        public ConfirmationApproval build() {
            return new ConfirmationApproval(this);
        }
    }
}
