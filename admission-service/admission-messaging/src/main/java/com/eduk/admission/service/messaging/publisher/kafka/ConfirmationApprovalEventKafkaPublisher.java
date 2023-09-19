package com.eduk.admission.service.messaging.publisher.kafka;

import com.eduk.admission.service.domain.config.ConfirmationServiceConfigData;
import com.eduk.admission.service.domain.outbox.model.approval.ConfirmationApprovalOutboxMessage;
import com.eduk.admission.service.domain.ports.output.message.publisher.financeapproval.FinanceApprovalRequestMessagePublisher;
import com.eduk.admission.service.messaging.mapper.ConfirmationMessagingDataMapper;
import com.eduk.domain.event.payload.ConfirmationApprovalEventPayload;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalRequestAvroModel;
import com.eduk.kafka.producer.KafkaMessageHelper;
import com.eduk.kafka.producer.service.KafkaProducer;
import com.eduk.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class ConfirmationApprovalEventKafkaPublisher implements FinanceApprovalRequestMessagePublisher {

    private final ConfirmationMessagingDataMapper confirmationMessagingDataMapper;
    private final KafkaProducer<String, FinanceApprovalRequestAvroModel> kafkaProducer;
    private final ConfirmationServiceConfigData confirmationServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public ConfirmationApprovalEventKafkaPublisher(ConfirmationMessagingDataMapper confirmationMessagingDataMapper,
                                                   KafkaProducer<String, FinanceApprovalRequestAvroModel> kafkaProducer,
                                                   ConfirmationServiceConfigData confirmationServiceConfigData,
                                                   KafkaMessageHelper kafkaMessageHelper) {
        this.confirmationMessagingDataMapper = confirmationMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.confirmationServiceConfigData = confirmationServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }


    @Override
    public void publish(ConfirmationApprovalOutboxMessage confirmationApprovalOutboxMessage,
                        BiConsumer<ConfirmationApprovalOutboxMessage, OutboxStatus> outboxCallback) {
        ConfirmationApprovalEventPayload confirmationApprovalEventPayload =
                kafkaMessageHelper.getConfirmationEventPayload(confirmationApprovalOutboxMessage.getPayload(),
                        ConfirmationApprovalEventPayload.class);

        String sagaId = confirmationApprovalOutboxMessage.getSagaId().toString();

        log.info("Received ConfirmationApprovalOutboxMessage for confirmation id: {} and saga id: {}",
                confirmationApprovalEventPayload.getConfirmationId(),
                sagaId);

        try {
            FinanceApprovalRequestAvroModel financeApprovalRequestAvroModel =
                    confirmationMessagingDataMapper
                            .confirmationApprovalEventToFinanceApprovalRequestAvroModel(sagaId,
                                    confirmationApprovalEventPayload);

            kafkaProducer.send(confirmationServiceConfigData.getFinanceApprovalRequestTopicName(),
                    sagaId,
                    financeApprovalRequestAvroModel,
                    kafkaMessageHelper.getKafkaCallback(confirmationServiceConfigData.getFinanceApprovalRequestTopicName(),
                            financeApprovalRequestAvroModel,
                            confirmationApprovalOutboxMessage,
                            outboxCallback,
                            confirmationApprovalEventPayload.getConfirmationId(),
                            "FinanceApprovalRequestAvroModel"));

            log.info("ConfirmationApprovalEventPayload sent to kafka for confirmation id: {} and saga id: {}",
                    financeApprovalRequestAvroModel.getConfirmationId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending ConfirmationApprovalEventPayload to kafka for confirmation id: {} and saga id: {}," +
                    " error: {}", confirmationApprovalEventPayload.getConfirmationId(), sagaId, e.getMessage());
        }


    }
}
