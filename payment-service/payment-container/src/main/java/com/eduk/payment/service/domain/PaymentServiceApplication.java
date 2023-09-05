package com.eduk.payment.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.eduk.payment.service.dataaccess")
@EnableJpaRepositories(basePackages = "com.eduk.payment.service.dataaccess")
@SpringBootApplication(scanBasePackages = "com.eduk")
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class,args);
    }
}
