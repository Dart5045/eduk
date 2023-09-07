package com.eduk.admission.service.messaging.listener.kafka;

import com.eduk.admission.service.messaging.mapper.ConfirmationMessagingDataMapper;
import com.eduk.kafka.confirmation.avro.model.PaymentResponseAvroModel;
import com.eduk.kafka.confirmation.avro.model.PaymentStatus;
import com.eduk.admission.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import com.eduk.kafka.consumer.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseAvroModel> {
    private final PaymentResponseMessageListener paymentResponseMessageListener;
    private final ConfirmationMessagingDataMapper confirmationMessagingDataMapper;

    public PaymentResponseKafkaListener(PaymentResponseMessageListener paymentResponseMessageListener
            , ConfirmationMessagingDataMapper confirmationMessagingDataMapper) {
        this.paymentResponseMessageListener = paymentResponseMessageListener;
        this.confirmationMessagingDataMapper = confirmationMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
    topics = "${admission-service.payment-response-topic-name}")
    public void receive(
              @Payload List<PaymentResponseAvroModel> messages
            , @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys
            , @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions
            , @Header(KafkaHeaders.OFFSET)  List<Long> offsets) {
        messages.forEach(
                paymentResponseAvroModel ->
                {
                    if(PaymentStatus.COMPLETED  == paymentResponseAvroModel.getPaymentStatus()){
                        log.info("Processing successfully payment for confirmation id :{}",paymentResponseAvroModel.getConfirmationId());
                        paymentResponseMessageListener
                                .paymentCompleted(confirmationMessagingDataMapper
                                .paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel));
                    } else if (PaymentStatus.CANCELLED == paymentResponseAvroModel.getPaymentStatus() ||
                            PaymentStatus.FAILED == paymentResponseAvroModel.getPaymentStatus()
                    ) {
                        log.info("Processing unsuccessfully payment for confirmation id :{}",paymentResponseAvroModel.getConfirmationId());
                        paymentResponseMessageListener
                                .paymentCancelled(
                                        confirmationMessagingDataMapper
                                        .paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel));
                    }
                }
        );

    }
}
