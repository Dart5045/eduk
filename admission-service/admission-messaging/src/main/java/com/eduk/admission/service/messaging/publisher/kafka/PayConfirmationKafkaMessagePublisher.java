package com.eduk.admission.service.messaging.publisher.kafka;

import com.eduk.admission.service.domain.config.ConfirmationServiceConfigData;
import com.eduk.admission.service.domain.event.ConfirmationPaidEvent;
import com.eduk.admission.service.domain.ports.output.message.publisher.financeapproval.ConfirmationPaidFinanceRequestMessagePublisher;
import com.eduk.admission.service.messaging.mapper.ConfirmationMessagingDataMapper;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalRequestAvroModel;
import com.eduk.kafka.producer.KafkaMessageHelper;
import com.eduk.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayConfirmationKafkaMessagePublisher implements ConfirmationPaidFinanceRequestMessagePublisher {

    private final ConfirmationMessagingDataMapper confirmationMessagingDataMapper;
    private final ConfirmationServiceConfigData confirmationServiceConfigData;
    private final KafkaProducer<String, FinanceApprovalRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper confirmationKafkaMessageHelper;

    public PayConfirmationKafkaMessagePublisher(ConfirmationMessagingDataMapper confirmationMessagingDataMapper,
                                                ConfirmationServiceConfigData confirmationServiceConfigData,
                                                KafkaProducer<String, FinanceApprovalRequestAvroModel> kafkaProducer,
                                                KafkaMessageHelper confirmationKafkaMessageHelper) {
        this.confirmationMessagingDataMapper = confirmationMessagingDataMapper;
        this.confirmationServiceConfigData = confirmationServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.confirmationKafkaMessageHelper = confirmationKafkaMessageHelper;
    }

    @Override
    public void publish(ConfirmationPaidEvent domainEvent) {
        String confirmationId = domainEvent.getConfirmation().getId().getValue().toString();

        try {
            FinanceApprovalRequestAvroModel financeApprovalRequestAvroModel =
                    confirmationMessagingDataMapper.confirmationPaidEventToFinanceApprovalRequestAvroModel(domainEvent);

            kafkaProducer.send(confirmationServiceConfigData.getFinanceApprovalRequestTopicName(),
                    confirmationId,
                    financeApprovalRequestAvroModel,
                    confirmationKafkaMessageHelper
                            .getKafkaCallback(confirmationServiceConfigData.getFinanceApprovalRequestTopicName(),
                                    financeApprovalRequestAvroModel,
                                    confirmationId,
                                    "FinanceApprovalRequestAvroModel"));

            log.info("FinanceApprovalRequestAvroModel sent to kafka for confirmation id: {}", confirmationId);
        } catch (Exception e) {
            log.error("Error while sending FinanceApprovalRequestAvroModel message" +
                    " to kafka with confirmation id: {}, error: {}", confirmationId, e.getMessage());
        }
    }
}
