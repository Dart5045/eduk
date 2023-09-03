package com.eduk.student.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.eduk")
public class StudentService {
    public static void main(String[] args) {
        SpringApplication.run(StudentService.class, args);
    }
}
