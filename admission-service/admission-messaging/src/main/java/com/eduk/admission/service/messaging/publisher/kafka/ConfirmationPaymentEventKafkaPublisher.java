package com.eduk.admission.service.messaging.publisher.kafka;

import com.eduk.admission.service.domain.config.ConfirmationServiceConfigData;
import com.eduk.admission.service.domain.outbox.model.payment.ConfirmationPaymentOutboxMessage;
import com.eduk.admission.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.eduk.admission.service.messaging.mapper.ConfirmationMessagingDataMapper;
import com.eduk.domain.event.payload.ConfirmationPaymentEventPayload;
import com.eduk.kafka.confirmation.avro.model.PaymentRequestAvroModel;
import com.eduk.kafka.producer.KafkaMessageHelper;
import com.eduk.kafka.producer.service.KafkaProducer;
import com.eduk.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class ConfirmationPaymentEventKafkaPublisher implements PaymentRequestMessagePublisher {

    private final ConfirmationMessagingDataMapper confirmationMessagingDataMapper;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final ConfirmationServiceConfigData confirmationServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public ConfirmationPaymentEventKafkaPublisher(ConfirmationMessagingDataMapper confirmationMessagingDataMapper,
                                                  KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer,
                                                  ConfirmationServiceConfigData confirmationServiceConfigData,
                                                  KafkaMessageHelper kafkaMessageHelper) {
        this.confirmationMessagingDataMapper = confirmationMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.confirmationServiceConfigData = confirmationServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(ConfirmationPaymentOutboxMessage confirmationPaymentOutboxMessage,
                        BiConsumer<ConfirmationPaymentOutboxMessage, OutboxStatus> outboxCallback) {
        ConfirmationPaymentEventPayload confirmationPaymentEventPayload =
                kafkaMessageHelper.getConfirmationEventPayload(confirmationPaymentOutboxMessage.getPayload(),
                        ConfirmationPaymentEventPayload.class);

        String sagaId = confirmationPaymentOutboxMessage.getSagaId().toString();

        log.info("Received ConfirmationPaymentOutboxMessage for confirmation id: {} and saga id: {}",
                confirmationPaymentEventPayload.getConfirmationId(),
                sagaId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = confirmationMessagingDataMapper
                    .confirmationPaymentEventToPaymentRequestAvroModel(sagaId, confirmationPaymentEventPayload);

            kafkaProducer.send(confirmationServiceConfigData.getPaymentRequestTopicName(),
                    sagaId,
                    paymentRequestAvroModel,
                    kafkaMessageHelper.getKafkaCallback(confirmationServiceConfigData.getPaymentRequestTopicName(),
                            paymentRequestAvroModel,
                            confirmationPaymentOutboxMessage,
                            outboxCallback,
                            confirmationPaymentEventPayload.getConfirmationId(),
                            "PaymentRequestAvroModel"));

            log.info("ConfirmationPaymentEventPayload sent to Kafka for confirmation id: {} and saga id: {}",
                    confirmationPaymentEventPayload.getConfirmationId(), sagaId);
        } catch (Exception e) {
           log.error("Error while sending ConfirmationPaymentEventPayload" +
                           " to kafka with confirmation id: {} and saga id: {}, error: {}",
                   confirmationPaymentEventPayload.getConfirmationId(), sagaId, e.getMessage());
        }


    }
}
