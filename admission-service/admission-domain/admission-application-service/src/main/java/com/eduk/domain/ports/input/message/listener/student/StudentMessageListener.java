package com.eduk.domain.ports.input.message.listener.student;


import com.eduk.domain.dto.message.StudentModel;

public interface StudentMessageListener {

    void studentCreated(StudentModel studentModel);
}
