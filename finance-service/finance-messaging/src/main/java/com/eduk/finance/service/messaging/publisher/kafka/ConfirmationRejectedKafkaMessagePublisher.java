package com.eduk.finance.service.messaging.publisher.kafka;

import com.eduk.finance.service.domain.config.FinanceServiceConfigData;
import com.eduk.finance.service.domain.event.ConfirmationRejectedEvent;
import com.eduk.finance.service.domain.ports.output.message.publisher.ConfirmationRejectedMessagePublisher;
import com.eduk.finance.service.messaging.mapper.FinanceMessagingDataMapper;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalResponseAvroModel;
import com.eduk.kafka.producer.KafkaMessageHelper;
import com.eduk.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfirmationRejectedKafkaMessagePublisher implements ConfirmationRejectedMessagePublisher {

    private final FinanceMessagingDataMapper restaurantMessagingDataMapper;
    private final KafkaProducer<String, FinanceApprovalResponseAvroModel> kafkaProducer;
    private final FinanceServiceConfigData restaurantServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public ConfirmationRejectedKafkaMessagePublisher(FinanceMessagingDataMapper restaurantMessagingDataMapper,
                                                     KafkaProducer<String, FinanceApprovalResponseAvroModel> kafkaProducer,
                                                     FinanceServiceConfigData restaurantServiceConfigData,
                                                     KafkaMessageHelper kafkaMessageHelper) {
        this.restaurantMessagingDataMapper = restaurantMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.restaurantServiceConfigData = restaurantServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(ConfirmationRejectedEvent confirmationRejectedEvent) {
        String confirmationId = confirmationRejectedEvent.getConfirmationApproval().getConfirmationId().getValue().toString();

        log.info("Received ConfirmationRejectedEvent for confirmation id: {}", confirmationId);

        try {
            FinanceApprovalResponseAvroModel restaurantApprovalResponseAvroModel =
                    restaurantMessagingDataMapper
                            .confirmationRejectedEventToFinanceApprovalResponseAvroModel(confirmationRejectedEvent);

            kafkaProducer.send(restaurantServiceConfigData.getFinanceApprovalResponseTopicName(),
                    confirmationId,
                    restaurantApprovalResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallback(restaurantServiceConfigData
                                    .getFinanceApprovalResponseTopicName(),
                            restaurantApprovalResponseAvroModel,
                            confirmationId,
                            "FinanceApprovalResponseAvroModel"));

            log.info("FinanceApprovalResponseAvroModel sent to kafka at: {}", System.nanoTime());
        } catch (Exception e) {
            log.error("Error while sending FinanceApprovalResponseAvroModel message" +
                    " to kafka with confirmation id: {}, error: {}", confirmationId, e.getMessage());
        }
    }

}
