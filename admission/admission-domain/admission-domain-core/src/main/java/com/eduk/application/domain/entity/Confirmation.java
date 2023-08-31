package com.eduk.application.domain.entity;

import com.eduk.application.domain.exception.ApplicationDomainException;
import com.eduk.application.domain.valueobject.ConfirmationStatus;
import com.eduk.domain.entity.BaseEntity;
import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Confirmation extends BaseEntity<ConfirmationId> {
    private final ApplicationId applicationId;
    private ConfirmationStatus confirmationStatus;

    private LocalDateTime dateConfirmed;
    private Money amount;

    private Confirmation(Builder builder) {
        super.setId(builder.confirmationId);
        applicationId = builder.applicationId;
        confirmationStatus = builder.confirmationStatus;
        amount = builder.amount;
    }

    public void payConfirmation(){
        if(confirmationStatus!= ConfirmationStatus.PAID){
            throw new ApplicationDomainException("Application is not in the correct state for pay operation!");
        }
        dateConfirmed = LocalDateTime.now();
    }


    public void initCancellingFeePayment(List<String> failureMessages){
        if(confirmationStatus!= ConfirmationStatus.PAID){
            throw new ApplicationDomainException("Application is not in the correct state for initCancel operation!");
        }
        confirmationStatus = ConfirmationStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if(confirmationStatus!= ConfirmationStatus.PAID){
            throw new ApplicationDomainException("Application is not in the correct state for initCancel operation!");
        }
    }

    public void cancelFeePayment( List<String> failureMessages){
        if(!(confirmationStatus == ConfirmationStatus.CANCELLING || confirmationStatus == ConfirmationStatus.PENDING)){
            throw new ApplicationDomainException("Application is not in the correct state for cancel operation!");
        }
        confirmationStatus = ConfirmationStatus.CANCELLED;
        updateFailureMessages(failureMessages);

    }

    public void validateConfirmation(){
        validateInitFeePayment();
    }

    private void validateInitFeePayment(){
        if(confirmationStatus == null || confirmationStatus.equals(ConfirmationStatus.PAID)){
            throw new ApplicationDomainException("Application is not in the correct state for initialization");
        }
    }


    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeConfirmation(){
        this.setId(new ConfirmationId(UUID.randomUUID()));
        amount = new Money(new BigDecimal(50));
        confirmationStatus = ConfirmationStatus.PENDING;
    }

    public ApplicationId getApplicationId() {
        return applicationId;
    }

    public ConfirmationStatus getConfirmationStatus() {
        return confirmationStatus;
    }

    public Money getAmount() {
        return amount;
    }


    public static final class Builder {
        private ApplicationId applicationId;
        private ConfirmationStatus confirmationStatus;
        private Money amount;
        private ConfirmationId confirmationId;

        private Builder() {
        }

        public Builder applicationId(ApplicationId val) {
            applicationId = val;
            return this;
        }

        public Builder confirmationStatus(ConfirmationStatus val) {
            confirmationStatus = val;
            return this;
        }

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder confirmationId(ConfirmationId val) {
            confirmationId = val;
            return this;
        }

        public Confirmation build() {
            return new Confirmation(this);
        }
    }
}