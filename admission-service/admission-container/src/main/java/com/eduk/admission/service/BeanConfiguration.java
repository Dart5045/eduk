package com.eduk.admission.service;

import com.eduk.admission.service.domain.ConfirmationDomainService;
import com.eduk.admission.service.domain.ConfirmationDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public ConfirmationDomainService confirmationDomainService(){
        return new ConfirmationDomainServiceImpl();
    }
}
