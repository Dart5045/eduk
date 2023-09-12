package com.eduk.admission.service.domain.ports.input.message.listener.student;


import com.eduk.admission.service.domain.dto.message.StudentModel;

public interface StudentMessageListener {

    void studentCreated(StudentModel studentModel);
}
