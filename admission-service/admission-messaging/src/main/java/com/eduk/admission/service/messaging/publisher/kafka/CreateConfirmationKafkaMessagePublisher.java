package com.eduk.admission.service.messaging.publisher.kafka;

import com.eduk.admission.service.domain.event.ConfirmationCreatedEvent;
import com.eduk.kafka.producer.KafkaMessageHelper;
import com.eduk.kafka.producer.service.KafkaProducer;
import com.eduk.admission.service.messaging.mapper.ConfirmationMessagingDataMapper;
import com.eduk.kafka.confirmation.avro.model.PaymentRequestAvroModel;
import com.eduk.admission.service.domain.config.ConfirmationServiceConfigData;
import com.eduk.admission.service.domain.ports.output.message.publisher.payment.ConfirmationCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateConfirmationKafkaMessagePublisher implements ConfirmationCreatedPaymentRequestMessagePublisher {
    private final ConfirmationMessagingDataMapper confirmationMessagingDataMapper;
    private final ConfirmationServiceConfigData confirmationServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;

    private final KafkaMessageHelper confirmationKafkaMessageHelper;

    public CreateConfirmationKafkaMessagePublisher(
            ConfirmationMessagingDataMapper confirmationMessagingDataMapper
            , ConfirmationServiceConfigData confirmationServiceConfigData
            , KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, KafkaMessageHelper confirmationKafkaMessageHelper) {
        this.confirmationMessagingDataMapper = confirmationMessagingDataMapper;
        this.confirmationServiceConfigData = confirmationServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.confirmationKafkaMessageHelper = confirmationKafkaMessageHelper;
    }

    @Override
    public void publish(ConfirmationCreatedEvent domainEvent) {
        String confirmationId = domainEvent.getConfirmation().getId().getValue().toString();
        log.info("Received confirmationCreatedEvent for confirmation  id :{}",confirmationId);
        try {
            PaymentRequestAvroModel paymentRequestAvroModel = confirmationMessagingDataMapper
                    .confirmationCreatedEventToPaymentRequestAvroModel( domainEvent);
            kafkaProducer.send(confirmationServiceConfigData.getPaymentRequestTopicName()
                    ,confirmationId
                    ,paymentRequestAvroModel
                    ,confirmationKafkaMessageHelper
                            .getKafkaCallback(confirmationServiceConfigData
                                    .getPaymentRequestTopicName()
                                    ,paymentRequestAvroModel
                                    , confirmationId
                                    , "PaymentRequestAvroModel"));
            log.info("PaymentRequestAvroModel sent to kafka for confirmation id:{}",paymentRequestAvroModel.getConfirmationId());
        }catch (Exception e){
            log.error("Error while sending PaymentRequestAvroModel message "+
                    "to kafka with confirmation id:{}, error:{}",confirmationId,e);
        }
    }

}
