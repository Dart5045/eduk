package com.eduk.student.service.dataacess.student.mapper;

import com.eduk.domain.valueobject.StudentId;
import com.eduk.student.service.dataacess.student.entity.StudentEntity;
import com.eduk.student.service.domain.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentDataAccessMapper {

    public Student studentEntityToStudent(StudentEntity studentEntity) {
        return new Student(new StudentId(studentEntity.getId()),
                studentEntity.getFirstName(),
                studentEntity.getLastName(),
                studentEntity.getEmail());
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
