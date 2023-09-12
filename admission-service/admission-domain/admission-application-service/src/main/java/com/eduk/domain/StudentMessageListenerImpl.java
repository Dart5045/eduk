package com.eduk.domain;

import com.eduk.domain.dto.message.StudentModel;
import com.eduk.domain.entity.Student;
import com.eduk.domain.exception.ConfirmationDomainException;
import com.eduk.domain.mapper.ConfirmationDataMapper;
import com.eduk.domain.ports.input.message.listener.student.StudentMessageListener;
import com.eduk.domain.ports.output.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentMessageListenerImpl implements StudentMessageListener {

    private final StudentRepository studentRepository;
    private final ConfirmationDataMapper orderDataMapper;

    public StudentMessageListenerImpl(StudentRepository studentRepository, ConfirmationDataMapper orderDataMapper) {
        this.studentRepository = studentRepository;
        this.orderDataMapper = orderDataMapper;
    }


    @Override
    public void studentCreated(StudentModel studentModel) {
        Student student = studentRepository.save(orderDataMapper.studentModelToStudent(studentModel));
        if (student == null) {
            log.error("Student could not be created in order database with id: {}", studentModel.getId());
            throw new ConfirmationDomainException("Student could not be created in order database with id " +
                    studentModel.getId());
        }
        log.info("Student is created in order database with id: {}", student.getId());
    }
}
