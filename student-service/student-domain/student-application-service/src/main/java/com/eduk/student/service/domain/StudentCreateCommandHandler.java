package com.eduk.student.service.domain;

import com.eduk.student.service.domain.create.CreateStudentCommand;
import com.eduk.student.service.domain.entity.Student;
import com.eduk.student.service.domain.event.StudentCreatedEvent;
import com.eduk.student.service.domain.exception.StudentDomainException;
import com.eduk.student.service.domain.mapper.StudentDataMapper;
import com.eduk.student.service.domain.ports.output.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
class StudentCreateCommandHandler {

    private final StudentDomainService studentDomainService;

    private final StudentRepository studentRepository;

    private final StudentDataMapper studentDataMapper;

    public StudentCreateCommandHandler(StudentDomainService studentDomainService,
                                       StudentRepository studentRepository,
                                       StudentDataMapper studentDataMapper) {
        this.studentDomainService = studentDomainService;
        this.studentRepository = studentRepository;
        this.studentDataMapper = studentDataMapper;
    }

    @Transactional
    public StudentCreatedEvent createStudent(CreateStudentCommand createStudentCommand) {
        Student student = studentDataMapper.createStudentCommandToStudent(createStudentCommand);
        StudentCreatedEvent studentCreatedEvent = studentDomainService.validateAndInitiateStudent(student);
        Student savedStudent = studentRepository.createStudent(student);
        if (savedStudent == null) {
            log.error("Could not save student with id: {}", createStudentCommand.getStudentId());
            throw new StudentDomainException("Could not save student with id " +
                    createStudentCommand.getStudentId());
        }
        log.info("Returning StudentCreatedEvent for student id: {}", createStudentCommand.getStudentId());
        return studentCreatedEvent;
    }
}
