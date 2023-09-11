package com.eduk.student.service.domain.ports.output.repository;

import com.eduk.student.service.domain.entity.Student;

public interface StudentRepository {
    Student createStudent(Student student);
}
