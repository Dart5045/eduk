package com.eduk.student.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = { "com.eduk.student.service.dataaccess", "com.eduk.dataaccess"})
@EntityScan(basePackages = { "com.eduk.student.service.dataaccess", "com.eduk.dataaccess" })
@SpringBootApplication(scanBasePackages = "com.eduk")
public class StudentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }

}
