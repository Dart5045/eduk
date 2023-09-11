package com.eduk.student.service.domain;

import com.eduk.student.service.domain.create.CreateStudentCommand;
import com.eduk.student.service.domain.create.CreateStudentResponse;
import com.eduk.student.service.domain.event.StudentCreatedEvent;
import com.eduk.student.service.domain.mapper.StudentDataMapper;
import com.eduk.student.service.domain.ports.input.service.StudentApplicationService;
import com.eduk.student.service.domain.ports.output.message.publisher.StudentMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class StudentApplicationServiceImpl implements StudentApplicationService {

    private final StudentCreateCommandHandler studentCreateCommandHandler;

    private final StudentDataMapper studentDataMapper;

    private final StudentMessagePublisher studentMessagePublisher;

    public StudentApplicationServiceImpl(StudentCreateCommandHandler studentCreateCommandHandler,
                                         StudentDataMapper studentDataMapper,
                                         StudentMessagePublisher studentMessagePublisher) {
        this.studentCreateCommandHandler = studentCreateCommandHandler;
        this.studentDataMapper = studentDataMapper;
        this.studentMessagePublisher = studentMessagePublisher;
    }

    @Override
    public CreateStudentResponse createStudent(CreateStudentCommand createStudentCommand) {
        StudentCreatedEvent studentCreatedEvent = studentCreateCommandHandler.createStudent(createStudentCommand);
        studentMessagePublisher.publish(studentCreatedEvent);
        return studentDataMapper
                .studentToCreateStudentResponse(studentCreatedEvent.getStudent(),
                        "Student saved successfully!");
    }
}
