package com.eduk.student.service.domain.ports.output.message.publisher;

import com.eduk.student.service.domain.event.StudentCreatedEvent;

public interface StudentMessagePublisher{
    void publish(StudentCreatedEvent studentCreatedEvent);
}
