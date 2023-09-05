package com.eduk.paymenet.service.domain.ports.input.message.listener;

import com.eduk.paymenet.service.domain.dto.PaymentRequest;

public interface PaymentRequestMessageListener {
    void completePayment(PaymentRequest paymentRequest);
    void cancelPayment(PaymentRequest paymentRequest);
}
