package com.eduk.student.service.domain.event;

import com.eduk.domain.event.DomainEvent;
import com.eduk.student.service.domain.entity.Student;

import java.time.ZonedDateTime;

public class StudentCreatedEvent implements DomainEvent<Student> {
 private final Student student;
 private final ZonedDateTime createdAt;

    public StudentCreatedEvent(Student student, ZonedDateTime createdAt) {
        this.student = student;
        this.createdAt = createdAt;
    }
    public Student getStudent(){
        return student;
    }
}
