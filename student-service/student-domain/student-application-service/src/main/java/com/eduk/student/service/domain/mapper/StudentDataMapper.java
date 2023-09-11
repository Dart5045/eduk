package com.eduk.student.service.domain.mapper;

import com.eduk.domain.valueobject.StudentId;
import com.eduk.student.service.domain.create.CreateStudentCommand;
import com.eduk.student.service.domain.create.CreateStudentResponse;
import com.eduk.student.service.domain.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentDataMapper {
    public Student createStudentCommandToStudent(CreateStudentCommand createStudentCommand) {
        return new Student(new StudentId(createStudentCommand.getStudentId()),
                createStudentCommand.getFirstName(),
                createStudentCommand.getLastName(),
                createStudentCommand.getEmail());
    }

    public CreateStudentResponse studentToCreateStudentResponse(Student student, String message) {
        return new CreateStudentResponse(student.getId().getValue(), message);
    }
}
