package com.eduk.student.service.messaging.publisher.mapper;

import com.eduk.kafka.student.avro.model.StudentAvroModel;
import com.eduk.student.service.domain.event.StudentCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class StudentMessagingDataMapper {

    public StudentAvroModel paymentResponseAvroModelToPaymentResponse(StudentCreatedEvent
                                                                               studentCreatedEvent) {
        return StudentAvroModel.newBuilder()
                .setId(studentCreatedEvent.getStudent().getId().getValue().toString())
                .setEmail(studentCreatedEvent.getStudent().getEmail())
                .setFirstName(studentCreatedEvent.getStudent().getFirstName())
                .setLastName(studentCreatedEvent.getStudent().getLastName())
                .build();
    }
}
