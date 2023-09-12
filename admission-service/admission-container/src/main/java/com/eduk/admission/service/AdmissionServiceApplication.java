package com.eduk.admission.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = { "com.eduk.admission.service.dataaccess", "com.eduk.dataaccess" })
@EntityScan(basePackages = { "com.eduk.admission.service.dataaccess", "com.eduk.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.eduk")
@ComponentScan(basePackages = {"com.eduk.admission", "com.eduk.kafka", "com.eduk.domain"})
public class AdmissionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdmissionServiceApplication.class, args);
    }
}
