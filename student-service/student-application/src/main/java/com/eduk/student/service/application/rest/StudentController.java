package com.eduk.student.service.application.rest;

import com.eduk.student.service.domain.create.CreateStudentCommand;
import com.eduk.student.service.domain.create.CreateStudentResponse;
import com.eduk.student.service.domain.ports.input.service.StudentApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/app/v1/students", produces = "application/vnd.api.v1+json")
public class StudentController {

    private final StudentApplicationService studentApplicationService;

    public StudentController(StudentApplicationService studentApplicationService) {
        this.studentApplicationService = studentApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponse> createStudent(@RequestBody CreateStudentCommand
                                                                             createStudentCommand) {
        log.info("Creating student with username: {}", createStudentCommand.getEmail());
        CreateStudentResponse response = studentApplicationService.createStudent(createStudentCommand);
        return ResponseEntity.ok(response);
    }

}
