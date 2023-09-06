package com.eduk.service.domain;


import com.eduk.service.domain.dto.message.PaymentResponse;
import com.eduk.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.eduk.application.domain.entity.Confirmation.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Service
@Validated
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {


    private final ConfirmationPaymentSaga confirmationPaymentSaga;

    public PaymentResponseMessageListenerImpl(ConfirmationPaymentSaga confirmationPaymentSaga) {
        this.confirmationPaymentSaga = confirmationPaymentSaga;
    }

    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
        confirmationPaymentSaga.process(paymentResponse);
        log.info("Confirmation Payment Saga process operation is completed for order id: {}", paymentResponse.getConfirmationId());
    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
        confirmationPaymentSaga.rollback(paymentResponse);
        log.info("Confirmation is roll backed for order id: {} with failure messages: {}",
                paymentResponse.getConfirmationId(),
                String.join(FAILURE_MESSAGE_DELIMITER, paymentResponse.getFailureMessages()));
    }
}
