package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.entity.Student;
import com.eduk.admission.service.domain.ports.input.message.listener.student.StudentMessageListener;
import com.eduk.admission.service.domain.ports.output.repository.StudentRepository;
import com.eduk.admission.service.domain.exception.ConfirmationDomainException;
import com.eduk.admission.service.domain.dto.message.StudentModel;
import com.eduk.admission.service.domain.mapper.ConfirmationDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentMessageListenerImpl implements StudentMessageListener {

    private final StudentRepository studentRepository;
    private final ConfirmationDataMapper confirmationDataMapper;

    public StudentMessageListenerImpl(StudentRepository studentRepository, ConfirmationDataMapper confirmationDataMapper) {
        this.studentRepository = studentRepository;
        this.confirmationDataMapper = confirmationDataMapper;
    }

    @Override
    public void studentCreated(StudentModel studentModel) {
        Student student = studentRepository.save(confirmationDataMapper.studentModelToStudent(studentModel));
        if (student == null) {
            log.error("Student could not be created in confirmation database with id: {}", studentModel.getId());
            throw new ConfirmationDomainException("Student could not be created in confirmation database with id " +
                    studentModel.getId());
        }
        log.info("Student is created in confirmation database with id: {}", student.getId());
    }
}
