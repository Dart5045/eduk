package com.eduk.student.service;

import com.eduk.student.service.domain.StudentDomainService;
import com.eduk.student.service.domain.StudentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public StudentDomainService customerDomainService() {
        return new StudentDomainServiceImpl();
    }
}
