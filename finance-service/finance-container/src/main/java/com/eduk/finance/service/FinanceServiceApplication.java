package com.eduk.finance.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.eduk.finance.service.dataaccess", "com.eduk.dataaccess" })
@EntityScan(basePackages = { "com.eduk.finance.service.dataaccess", "com.eduk.dataaccess" })
@SpringBootApplication(scanBasePackages = "com.eduk")
public class FinanceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinanceServiceApplication.class, args);
    }
}
