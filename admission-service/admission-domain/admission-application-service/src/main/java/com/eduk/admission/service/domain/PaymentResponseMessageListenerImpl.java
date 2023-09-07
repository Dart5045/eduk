package com.eduk.admission.service.domain;


import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.dto.message.PaymentResponse;
import com.eduk.admission.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
                String.join(Confirmation.FAILURE_MESSAGE_DELIMITER, paymentResponse.getFailureMessages()));
    }
}
