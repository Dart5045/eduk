package com.eduk.admission.service.dataaccess.student.mapper;

import com.eduk.admission.service.dataaccess.student.entity.StudentEntity;
import com.eduk.admission.service.domain.entity.Student;
import com.eduk.domain.valueobject.StudentId;
import org.springframework.stereotype.Component;

@Component
public class StudentDataAccessMapper {

    public Student studentEntityToStudent(StudentEntity studentEntity) {
        return new Student(new StudentId(studentEntity.getId()));
    }

    public StudentEntity studentToStudentEntity(Student student) {
        return StudentEntity.builder()
                .id(student.getId().getValue())
                .email(student.getEmail())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .build();
    }
}
