package com.eduk.student.service.dataaccess.student.adapter;

import com.eduk.student.service.dataaccess.student.mapper.StudentDataAccessMapper;
import com.eduk.student.service.dataaccess.student.repository.StudentJpaRepository;
import com.eduk.student.service.domain.entity.Student;
import com.eduk.student.service.domain.ports.output.repository.StudentRepository;
import org.springframework.stereotype.Component;

@Component
public class StudentRepositoryImpl implements StudentRepository {
    private final StudentJpaRepository studentJpaRepository;
    private final StudentDataAccessMapper studentDataAccessMapper;

    public StudentRepositoryImpl(StudentJpaRepository studentJpaRepository, StudentDataAccessMapper studentDataAccessMapper) {
        this.studentJpaRepository = studentJpaRepository;
        this.studentDataAccessMapper = studentDataAccessMapper;
    }

    @Override
    public Student createStudent(Student student) {
        return studentDataAccessMapper.studentEntityToStudent(
                studentJpaRepository.save(studentDataAccessMapper.studentToStudentEntity(student)));
    }    
    
}
