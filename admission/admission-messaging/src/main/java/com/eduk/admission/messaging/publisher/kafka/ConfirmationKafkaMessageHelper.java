package com.eduk.admission.messaging.publisher.kafka;

import com.eduk.admission.messaging.mapper.ConfirmationMessagingDataMapper;
import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.kafka.confirmation.avro.model.PaymentRequestAvroModel;
import com.eduk.kafka.producer.service.KafkaProducer;
import com.eduk.service.domain.config.ConfirmationServiceConfigData;
import com.eduk.service.domain.ports.output.message.publisher.payment.ConfirmationCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class ConfirmationKafkaMessageHelper {

    public ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>>
        getKafkaCallback(String paymentRequestTopicName
            , PaymentRequestAvroModel paymentRequestAvroModel) {
        return new ListenableFutureCallback<SendResult<String, PaymentRequestAvroModel>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while sending PaymentRequestAvroModel"+
                        "message {} to topic {}",paymentRequestAvroModel.toString(),paymentRequestTopicName,ex);
            }

            @Override
            public void onSuccess(SendResult<String, PaymentRequestAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.error("Received successful response from kafka for confirmation id:{}"+
                        " topic: {} partition: {} offset:{} timestamp:{}"
                        ,paymentRequestAvroModel.getConfirmationId()
                        ,metadata.topic()
                        , metadata.partition()
                        , metadata.partition()
                        , metadata.offset()
                        , metadata.timestamp()
                        );

            }
        };
    }

}
