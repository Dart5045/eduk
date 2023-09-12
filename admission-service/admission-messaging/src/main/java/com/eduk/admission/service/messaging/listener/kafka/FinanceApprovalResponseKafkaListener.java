package com.eduk.admission.service.messaging.listener.kafka;

import com.eduk.admission.service.domain.ports.input.message.listener.financeapproval.FinanceApprovalResponseMessageListener;
import com.eduk.admission.service.messaging.mapper.ConfirmationMessagingDataMapper;
import com.eduk.kafka.confirmation.avro.model.ConfirmationApprovalStatus;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalResponseAvroModel;
import com.eduk.kafka.consumer.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.eduk.admission.service.domain.Confirmation.FAILURE_MESSAGE_DELIMITER;


@Slf4j
@Component
public class FinanceApprovalResponseKafkaListener implements KafkaConsumer<FinanceApprovalResponseAvroModel> {

    private final FinanceApprovalResponseMessageListener financeApprovalResponseMessageListener;
    private final ConfirmationMessagingDataMapper confirmationMessagingDataMapper;

    public FinanceApprovalResponseKafkaListener(FinanceApprovalResponseMessageListener
                                                           financeApprovalResponseMessageListener,
                                                ConfirmationMessagingDataMapper confirmationMessagingDataMapper) {
        this.financeApprovalResponseMessageListener = financeApprovalResponseMessageListener;
        this.confirmationMessagingDataMapper = confirmationMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.finance-approval-consumer-group-id}",
                topics = "${admission-service.finance-approval-response-topic-name}")
    public void receive(@Payload List<FinanceApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of finance approval responses received with keys {}, partitions {} and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(financeApprovalResponseAvroModel -> {
            if (ConfirmationApprovalStatus.APPROVED == financeApprovalResponseAvroModel.getConfirmationApprovalStatus()) {
                log.info("Processing approved confirmation for confirmation id: {}",
                        financeApprovalResponseAvroModel.getConfirmationId());
                financeApprovalResponseMessageListener.confirmationApproved(confirmationMessagingDataMapper
                        .approvalResponseAvroModelToApprovalResponse(financeApprovalResponseAvroModel));
            } else if (ConfirmationApprovalStatus.REJECTED == financeApprovalResponseAvroModel.getConfirmationApprovalStatus()) {
                log.info("Processing rejected confirmation for confirmation id: {}, with failure messages: {}",
                        financeApprovalResponseAvroModel.getConfirmationId(),
                        String.join(FAILURE_MESSAGE_DELIMITER,
                                financeApprovalResponseAvroModel.getFailureMessages()));
                financeApprovalResponseMessageListener.confirmationRejected(confirmationMessagingDataMapper
                        .approvalResponseAvroModelToApprovalResponse(financeApprovalResponseAvroModel));
            }
        });

    }
}
