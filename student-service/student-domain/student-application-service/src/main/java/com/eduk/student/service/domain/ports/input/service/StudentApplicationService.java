package com.eduk.student.service.domain.ports.input.service;

import com.eduk.student.service.domain.create.CreateStudentCommand;
import com.eduk.student.service.domain.create.CreateStudentResponse;
import jakarta.validation.Valid;

public interface StudentApplicationService {
    CreateStudentResponse createStudent(@Valid CreateStudentCommand createStudentCommand);
}
