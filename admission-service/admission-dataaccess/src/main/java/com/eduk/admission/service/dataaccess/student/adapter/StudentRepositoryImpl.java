package com.eduk.admission.service.dataaccess.student.adapter;

import com.eduk.admission.service.dataaccess.student.mapper.StudentDataAccessMapper;
import com.eduk.admission.service.dataaccess.student.repository.StudentJpaRepository;
import com.eduk.domain.entity.Student;
import com.eduk.domain.ports.output.repository.StudentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class StudentRepositoryImpl implements StudentRepository {

    private final StudentJpaRepository studentJpaRepository;
    private final StudentDataAccessMapper studentDataAccessMapper;

    public StudentRepositoryImpl(StudentJpaRepository studentJpaRepository,
                                  StudentDataAccessMapper studentDataAccessMapper) {
        this.studentJpaRepository = studentJpaRepository;
        this.studentDataAccessMapper = studentDataAccessMapper;
    }

    @Override
    public Optional<Student> findStudent(UUID studentId) {
        return studentJpaRepository.findById(studentId).map(studentDataAccessMapper::studentEntityToStudent);
    }

    @Override
    @Transactional
    public Student save(Student student) {
        return studentDataAccessMapper.studentEntityToStudent(
                studentJpaRepository.save(studentDataAccessMapper.studentToStudentEntity(student)));
    }
}
