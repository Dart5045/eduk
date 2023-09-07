package com.eduk.payment.service.dataaccess.payment.mapper;

import com.eduk.admission.service.domain.valueobject.ApplicationId;
import com.eduk.admission.service.domain.valueobject.ConfirmationId;
import com.eduk.admission.service.domain.valueobject.Money;
import com.eduk.payment.service.dataaccess.payment.entity.PaymentEntity;
import com.eduk.payment.service.domain.valueobject.PaymentId;
import com.eduk.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentDataAccessMapper {

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId().getValue())
                .applicationId(payment.getApplicationId().getValue())
                .confirmationId(payment.getConfirmationId().getValue())
                .price(payment.getPrice().getAmount())
                .status(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        return Payment.builder()
                .paymentId(new PaymentId(paymentEntity.getId()))
                .applicationId(new ApplicationId(paymentEntity.getApplicationId()))
                .confirmationId(new ConfirmationId(paymentEntity.getConfirmationId()))
                .price(new Money(paymentEntity.getPrice()))
                .createdAt(paymentEntity.getCreatedAt())
                .build();
    }

}
