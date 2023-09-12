package com.eduk.admission.service.domain.ports.input.message.listener.payment;

import com.eduk.admission.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
