package com.eduk.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.eduk.admission-dataaccess")
@EntityScan(basePackages = "com.eduk.admission-dataaccess")
@SpringBootApplication(scanBasePackages = "com.eduk")
public class AdmissionsService {
    public static void main(String[] args) {
        SpringApplication.run(AdmissionsService.class, args);
    }
}
