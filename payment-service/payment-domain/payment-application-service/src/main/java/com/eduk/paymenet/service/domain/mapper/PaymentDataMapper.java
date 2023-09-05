package com.eduk.paymenet.service.domain.mapper;

import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.domain.valueobject.Money;
import com.eduk.paymenet.service.domain.dto.PaymentRequest;
import com.eduk.payment.service.domain.entity.Payment;
import com.eduk.payment.service.domain.valueobject.PaymentId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {
    public Payment paymentRequestModelToPayment(PaymentRequest paymentRequest) {
        return Payment
                .builder()
                .confirmationId(new ConfirmationId(UUID.fromString(paymentRequest.getConfirmationId())))
                .applicationId(new ApplicationId(UUID.fromString(paymentRequest.getApplicationId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
}
