package com.eduk.finance.service.messaging.publisher.kafka;

import com.eduk.finance.service.domain.config.FinanceServiceConfigData;
import com.eduk.finance.service.domain.event.ConfirmationApprovedEvent;
import com.eduk.finance.service.domain.ports.output.message.publisher.ConfirmationApprovedMessagePublisher;
import com.eduk.finance.service.messaging.mapper.FinanceMessagingDataMapper;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalResponseAvroModel;
import com.eduk.kafka.producer.KafkaMessageHelper;
import com.eduk.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfirmationApprovedKafkaMessagePublisher implements ConfirmationApprovedMessagePublisher {

    private final FinanceMessagingDataMapper financeMessagingDataMapper;
    private final KafkaProducer<String, FinanceApprovalResponseAvroModel> kafkaProducer;
    private final FinanceServiceConfigData financeServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public ConfirmationApprovedKafkaMessagePublisher(FinanceMessagingDataMapper financeMessagingDataMapper,
                                                     KafkaProducer<String, FinanceApprovalResponseAvroModel> kafkaProducer,
                                                     FinanceServiceConfigData financeServiceConfigData,
                                                     KafkaMessageHelper kafkaMessageHelper) {
        this.financeMessagingDataMapper = financeMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.financeServiceConfigData = financeServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(ConfirmationApprovedEvent confirmationApprovedEvent) {
        String confirmationId = confirmationApprovedEvent.getConfirmationApproval().getConfirmationId().getValue().toString();

        log.info("Received ConfirmationApprovedEvent for confirmation id: {}", confirmationId);

        try {
            FinanceApprovalResponseAvroModel financeApprovalResponseAvroModel =
                    financeMessagingDataMapper
                            .confirmationApprovedEventToFinanceApprovalResponseAvroModel(confirmationApprovedEvent);

            kafkaProducer.send(financeServiceConfigData.getFinanceApprovalResponseTopicName(),
                    confirmationId,
                    financeApprovalResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallback(financeServiceConfigData
                                    .getFinanceApprovalResponseTopicName(),
                            financeApprovalResponseAvroModel,
                            confirmationId,
                            "FinanceApprovalResponseAvroModel"));

            log.info("FinanceApprovalResponseAvroModel sent to kafka at: {}", System.nanoTime());
        } catch (Exception e) {
            log.error("Error while sending FinanceApprovalResponseAvroModel message" +
                    " to kafka with confirmation id: {}, error: {}", confirmationId, e.getMessage());
        }
    }

}
