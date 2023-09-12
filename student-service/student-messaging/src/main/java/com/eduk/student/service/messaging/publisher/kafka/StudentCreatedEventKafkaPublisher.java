package com.eduk.student.service.messaging.publisher.kafka;

import com.eduk.kafka.confirmation.avro.model.PaymentRequestAvroModel;
import com.eduk.kafka.producer.KafkaMessageHelper;
import com.eduk.kafka.producer.service.KafkaProducer;
import com.eduk.kafka.student.avro.model.StudentAvroModel;
import com.eduk.student.service.domain.config.StudentServiceConfigData;
import com.eduk.student.service.domain.event.StudentCreatedEvent;
import com.eduk.student.service.domain.ports.output.message.publisher.StudentMessagePublisher;
import com.eduk.student.service.messaging.publisher.mapper.StudentMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class StudentCreatedEventKafkaPublisher implements StudentMessagePublisher {
    private final StudentMessagingDataMapper studentMessagingDataMapper;

    private final KafkaProducer<String, StudentAvroModel> kafkaProducer;

    private final StudentServiceConfigData studentServiceConfigData;

    public StudentCreatedEventKafkaPublisher(StudentMessagingDataMapper studentMessagingDataMapper,
                                              KafkaProducer<String, StudentAvroModel> kafkaProducer,
                                              StudentServiceConfigData studentServiceConfigData) {
        this.studentMessagingDataMapper = studentMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.studentServiceConfigData = studentServiceConfigData;
    }

    @Override
    public void publish(StudentCreatedEvent studentCreatedEvent) {
        log.info("Received StudentCreatedEvent for student id: {}",
                studentCreatedEvent.getStudent().getId().getValue());
        try {
            StudentAvroModel studentAvroModel = studentMessagingDataMapper
                    .paymentResponseAvroModelToPaymentResponse(studentCreatedEvent);

            kafkaProducer.send(
                    studentServiceConfigData.getStudentTopicName(),
                    studentAvroModel.getId().toString(),
                    studentAvroModel,
                    getCallback(studentServiceConfigData.getStudentTopicName(), studentAvroModel));

            log.info("StudentCreatedEvent sent to kafka for student id: {}",
                    studentAvroModel.getId());
        } catch (Exception e) {
            log.error("Error while sending StudentCreatedEvent to kafka for student id: {}," +
                    " error: {}", studentCreatedEvent.getStudent().getId().getValue(), e.getMessage());
        }
    }

    private ListenableFutureCallback<SendResult<String, StudentAvroModel>>
    getCallback(String topicName, StudentAvroModel message) {
        return new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, StudentAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            }
        };
    }

}
