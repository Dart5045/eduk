package com.eduk.finance.service.domain.entity;
import com.eduk.admission.service.domain.entity.AggregateRoot;
import com.eduk.admission.service.domain.valueobject.ConfirmationApprovalStatus;
import com.eduk.admission.service.domain.valueobject.ConfirmationStatus;
import com.eduk.admission.service.domain.valueobject.FinanceId;
import com.eduk.admission.service.domain.valueobject.Money;
import com.eduk.finance.service.domain.valueobject.ConfirmationApprovalId;

import java.util.List;
import java.util.UUID;

public class Finance extends AggregateRoot<FinanceId> {
   private ConfirmationApproval confirmationApproval;
   private boolean active;
   private final ConfirmationDetail confirmationDetail;

   public void validateConfirmation(List<String> failureMessages) {
       if (confirmationDetail.getConfirmationStatus() != ConfirmationStatus.PAID) {
           failureMessages.add("Payment is not completed for confirmation: " + confirmationDetail.getId());
       }
       Money totalAmount = confirmationDetail.getProducts().stream().map(product -> {
           if (!product.isAvailable()) {
               failureMessages.add("Product with id: " + product.getId().getValue()
                       + " is not available");
           }
           return product.getPrice().multiply(product.getQuantity());
       }).reduce(Money.ZERO, Money::add);

       if (!totalAmount.equals(confirmationDetail.getTotalAmount())) {
           failureMessages.add("Price total is not correct for confirmation: " + confirmationDetail.getId());
       }
   }

   public void constructConfirmationApproval(ConfirmationApprovalStatus confirmationApprovalStatus) {
       this.confirmationApproval = ConfirmationApproval.builder()
               .confirmationApprovalId(new ConfirmationApprovalId(UUID.randomUUID()))
               .financeId(this.getId())
               .confirmationId(this.getConfirmationDetail().getId())
               .approvalStatus(confirmationApprovalStatus)
               .build();
   }

    public void setActive(boolean active) {
        this.active = active;
    }

    private Finance(Builder builder) {
        setId(builder.financeId);
        confirmationApproval = builder.confirmationApproval;
        active = builder.active;
        confirmationDetail = builder.confirmationDetail;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ConfirmationApproval getConfirmationApproval() {
        return confirmationApproval;
    }

    public boolean isActive() {
        return active;
    }

    public ConfirmationDetail getConfirmationDetail() {
        return confirmationDetail;
    }

    public static final class Builder {
        private FinanceId financeId;
        private ConfirmationApproval confirmationApproval;
        private boolean active;
        private ConfirmationDetail confirmationDetail;

        private Builder() {
        }

        public Builder financeId(FinanceId val) {
            financeId = val;
            return this;
        }

        public Builder confirmationApproval(ConfirmationApproval val) {
            confirmationApproval = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Builder confirmationDetail(ConfirmationDetail val) {
            confirmationDetail = val;
            return this;
        }

        public Finance build() {
            return new Finance(this);
        }
    }
}
