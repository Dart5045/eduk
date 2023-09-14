package com.eduk.admission.service.domain.ports.output.repository;

import com.eduk.admission.service.domain.entity.Student;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {

    Optional<Student> findStudent(UUID studentId);

    Student save(Student student);
}
