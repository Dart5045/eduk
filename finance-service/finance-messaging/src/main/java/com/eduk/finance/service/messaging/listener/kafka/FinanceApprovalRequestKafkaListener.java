package com.eduk.finance.service.messaging.listener.kafka;

import com.eduk.finance.service.domain.ports.input.message.listener.FinanceApprovalRequestMessageListener;
import com.eduk.kafka.confirmation.avro.model.FinanceApprovalRequestAvroModel;
import com.eduk.finance.service.messaging.mapper.FinanceMessagingDataMapper;
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
public class FinanceApprovalRequestKafkaListener implements KafkaConsumer<FinanceApprovalRequestAvroModel> {

    private final FinanceApprovalRequestMessageListener financeApprovalRequestMessageListener;
    private final FinanceMessagingDataMapper restaurantMessagingDataMapper;

    public FinanceApprovalRequestKafkaListener(FinanceApprovalRequestMessageListener
                                                          restaurantApprovalRequestMessageListener,
                                               FinanceMessagingDataMapper
                                                          restaurantMessagingDataMapper) {
        this.financeApprovalRequestMessageListener = restaurantApprovalRequestMessageListener;
        this.restaurantMessagingDataMapper = restaurantMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
            topics = "${restaurant-service.restaurant-approval-request-topic-name}")
    public void receive(@Payload List<FinanceApprovalRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of confirmations approval requests received with keys {}, partitions {} and offsets {}" +
                        ", sending for restaurant approval",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(restaurantApprovalRequestAvroModel -> {
            log.info("Processing confirmation approval for confirmation id: {}", restaurantApprovalRequestAvroModel.getConfirmationId());
            financeApprovalRequestMessageListener.approveConfirmation(restaurantMessagingDataMapper.
                    restaurantApprovalRequestAvroModelToFinanceApproval(restaurantApprovalRequestAvroModel));
        });
    }

}
