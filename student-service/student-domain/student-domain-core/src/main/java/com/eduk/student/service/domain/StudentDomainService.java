package com.eduk.student.service.domain;

import com.eduk.student.service.domain.entity.Student;
import com.eduk.student.service.domain.event.StudentCreatedEvent;

public interface StudentDomainService {
    StudentCreatedEvent validateAndInitiateStudent(Student student);
}
