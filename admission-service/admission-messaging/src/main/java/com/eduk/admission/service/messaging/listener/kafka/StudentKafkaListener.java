package com.eduk.admission.service.messaging.listener.kafka;

import com.eduk.admission.service.domain.ports.input.message.listener.student.StudentMessageListener;
import com.eduk.admission.service.messaging.mapper.ConfirmationMessagingDataMapper;
import com.eduk.kafka.consumer.KafkaConsumer;
import com.eduk.kafka.student.avro.model.StudentAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StudentKafkaListener implements KafkaConsumer<StudentAvroModel> {

    private final StudentMessageListener studentMessageListener;
    private final ConfirmationMessagingDataMapper confirmationMessagingDataMapper;

    public StudentKafkaListener(StudentMessageListener studentMessageListener,
                                ConfirmationMessagingDataMapper confirmationMessagingDataMapper) {
        this.studentMessageListener = studentMessageListener;
        this.confirmationMessagingDataMapper = confirmationMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.student-group-id}", topics = "${confirmation-service.student-topic-name}")
    public void receive(@Payload List<StudentAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of student create messages received with keys {}, partitions {} and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(studentAvroModel ->
                studentMessageListener.studentCreated(confirmationMessagingDataMapper
                        .studentAvroModeltoStudentModel(studentAvroModel)));
    }
}
