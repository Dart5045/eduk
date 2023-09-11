package com.eduk.student.service.domain;

import com.eduk.student.service.domain.entity.Student;
import com.eduk.student.service.domain.event.StudentCreatedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.eduk.domain.DomainConstants.UTC;

@Slf4j
public class StudentDomainServiceImpl implements StudentDomainService{

    @Override
    public StudentCreatedEvent validateAndInitiateStudent(Student student) {
        log.info("Student with id:{} is initiated", student.getId().getValue() );
        return new StudentCreatedEvent(student, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}
