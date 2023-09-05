package com.eduk.payment.service.domain.entity;

import com.eduk.domain.entity.AggregateRoot;
import com.eduk.domain.valueobject.*;
import com.eduk.payment.service.domain.valueobject.PaymentId;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class Payment extends AggregateRoot<PaymentId> {
    private final ApplicationId applicationId;
    private final ConfirmationId confirmationId;
    private final Money price;
    private PaymentStatus paymentStatus;
    private ZonedDateTime createdAt;

    public void initializePayment(){
        setId(new PaymentId(UUID.randomUUID()));
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public void validatePayment(List<String> failureMessages){
        if(price == null || !price.isGreaterThanZero()){
            failureMessages.add("Total price must be greater than zero!");
        }
    }

    public void updateStatus(PaymentStatus paymentStatus){
        this.paymentStatus = paymentStatus;
    }

    private Payment(Builder builder) {
        super.setId(builder.paymentId);
        applicationId = builder.applicationId;
        confirmationId = builder.confirmationId;
        price = builder.price;
        paymentStatus = builder.paymentStatus;
        createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ApplicationId getApplicationId() {
        return applicationId;
    }

    public ConfirmationId getConfirmationId() {
        return confirmationId;
    }

    public Money getPrice() {
        return price;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }


    public static final class Builder {
        private PaymentId paymentId;
        private ApplicationId applicationId;
        private ConfirmationId confirmationId;
        private Money price;
        private PaymentStatus paymentStatus;
        private ZonedDateTime createdAt;

        private Builder() {
        }

        public Builder paymentId(PaymentId val) {
            paymentId = val;
            return this;
        }

        public Builder applicationId(ApplicationId val) {
            applicationId = val;
            return this;
        }

        public Builder confirmationId(ConfirmationId val) {
            confirmationId = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder paymentStatus(PaymentStatus val) {
            paymentStatus = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
