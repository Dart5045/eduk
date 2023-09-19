package com.eduk.finance.service.messaging.publisher.kafka;

import com.eduk.finance.service.domain.config.FinanceServiceConfigData;
import com.eduk.finance.service.domain.outbox.model.ConfirmationEventPayload;
import com.eduk.finance.service.domain.outbox.model.ConfirmationOutboxMessage;
import com.eduk.finance.service.domain.ports.output.message.publisher.FinanceApprovalResponseMessagePublisher;
import com.eduk.finance.service.messaging.mapper.FinanceMessagingDataMapper;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalResponseAvroModel;
import com.eduk.kafka.producer.KafkaMessageHelper;
import com.eduk.kafka.producer.service.KafkaProducer;
import com.eduk.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class FinanceApprovalEventKafkaPublisher implements FinanceApprovalResponseMessagePublisher {
    private final FinanceMessagingDataMapper financeMessagingDataMapper;
    private final KafkaProducer<String, FinanceApprovalResponseAvroModel> kafkaProducer;
    private final FinanceServiceConfigData financeServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public FinanceApprovalEventKafkaPublisher(FinanceMessagingDataMapper dataMapper,
                                                 KafkaProducer<String, FinanceApprovalResponseAvroModel>
                                                         kafkaProducer,
                                                 FinanceServiceConfigData financeServiceConfigData,
                                                 KafkaMessageHelper kafkaMessageHelper) {
        this.financeMessagingDataMapper = dataMapper;
        this.kafkaProducer = kafkaProducer;
        this.financeServiceConfigData = financeServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }


    @Override
    public void publish(ConfirmationOutboxMessage confirmationOutboxMessage,
                        BiConsumer<ConfirmationOutboxMessage, OutboxStatus> outboxCallback) {
        ConfirmationEventPayload confirmationEventPayload =
                kafkaMessageHelper.getConfirmationEventPayload(confirmationOutboxMessage.getPayload(),
                        ConfirmationEventPayload.class);

        String sagaId = confirmationOutboxMessage.getSagaId().toString();

        log.info("Received ConfirmationOutboxMessage for confirmation id: {} and saga id: {}",
                confirmationEventPayload.getConfirmationId(),
                sagaId);
        try {
            FinanceApprovalResponseAvroModel financeApprovalResponseAvroModel =
                    financeMessagingDataMapper
                            .confirmationEventPayloadToFinanceApprovalResponseAvroModel(sagaId, confirmationEventPayload);

            kafkaProducer.send(financeServiceConfigData.getFinanceApprovalResponseTopicName()
                    , sagaId
                    , financeApprovalResponseAvroModel
                    , kafkaMessageHelper.getKafkaCallback(
                            financeServiceConfigData.getFinanceApprovalResponseTopicName(),
                            financeApprovalResponseAvroModel,
                            confirmationOutboxMessage,
                            outboxCallback,
                            confirmationEventPayload.getConfirmationId(),
                            "FinanceApprovalResponseAvroModel"));

            log.info("FinanceApprovalResponseAvroModel sent to kafka for confirmation id: {} and saga id: {}",
                    financeApprovalResponseAvroModel.getConfirmationId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending FinanceApprovalResponseAvroModel message" +
                            " to kafka with confirmation id: {} and saga id: {}, error: {}",
                    confirmationEventPayload.getConfirmationId(), sagaId, e.getMessage());
        }
    }


}

