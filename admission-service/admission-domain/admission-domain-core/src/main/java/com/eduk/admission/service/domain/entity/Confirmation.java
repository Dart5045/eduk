package com.eduk.admission.service.domain.entity;

import com.eduk.admission.service.domain.exception.ConfirmationDomainException;
import com.eduk.admission.service.domain.valueobject.ConfirmationStatus;
import com.eduk.admission.service.domain.valueobject.TrackingId;
import com.eduk.admission.service.domain.valueobject.ApplicationId;
import com.eduk.admission.service.domain.valueobject.ConfirmationId;
import com.eduk.admission.service.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Confirmation extends BaseEntity<ConfirmationId> {
    public static final String  FAILURE_MESSAGE_DELIMITER = String.valueOf(',');

    private final ApplicationId applicationId;
    private ConfirmationStatus confirmationStatus;

    private LocalDateTime dateConfirmed;
    private TrackingId trackingId;
    private Money amount;
    private List<String> failureMessages;

    private Confirmation(Builder builder) {
        super.setId(builder.confirmationId);
        applicationId = builder.applicationId;
        confirmationStatus = builder.confirmationStatus;
        dateConfirmed = builder.dateConfirmed;
        trackingId = builder.trackingId;
        setAmount(builder.amount);
        failureMessages = builder.failureMessages;
    }

    public static Builder builder() {
        return new Builder();
    }


    public void payConfirmation(){
        if(confirmationStatus!= ConfirmationStatus.PAID){
            throw new ConfirmationDomainException("Application is not in the correct state for pay operation!");
        }
        dateConfirmed = LocalDateTime.now();
    }


    public void initCancellingConfirmation(List<String> failureMessages){
        if(confirmationStatus!= ConfirmationStatus.PAID){
            throw new ConfirmationDomainException("Application is not in the correct state for initCancel operation!");
        }
        confirmationStatus = ConfirmationStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if(confirmationStatus!= ConfirmationStatus.PAID){
            throw new ConfirmationDomainException("Application is not in the correct state for initCancel operation!");
        }
    }

    public void cancelConfirmation(List<String> failureMessages){
        if(!(confirmationStatus == ConfirmationStatus.CANCELLING || confirmationStatus == ConfirmationStatus.PENDING)){
            throw new ConfirmationDomainException("Application is not in the correct state for cancel operation!");
        }
        confirmationStatus = ConfirmationStatus.CANCELLED;
        updateFailureMessages(failureMessages);

    }

    public void validateConfirmation(){
        validateInitFeePayment();
    }

    private void validateInitFeePayment(){
        if(confirmationStatus == null || confirmationStatus.equals(ConfirmationStatus.PAID)){
            throw new ConfirmationDomainException("Application is not in the correct state for initialization");
        }
    }


    public void setAmount(Money amount) {
        this.amount = amount;
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

    public LocalDateTime getDateConfirmed() {
        return dateConfirmed;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public Money getAmount() {
        return amount;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }


    public static final class Builder {
        private ApplicationId applicationId;
        private ConfirmationStatus confirmationStatus;
        private LocalDateTime dateConfirmed;
        private TrackingId trackingId;
        private Money amount;
        private List<String> failureMessages;
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

        public Builder dateConfirmed(LocalDateTime val) {
            dateConfirmed = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
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