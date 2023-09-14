package com.eduk.admission.service.domain.ports.output.message.publisher.payment;

import com.eduk.admission.service.domain.outbox.model.payment.ConfirmationPaymentOutboxMessage;
import com.eduk.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {

    void publish(ConfirmationPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<ConfirmationPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
