package com.eduk.domain;

import com.eduk.application.domain.ConfirmationDomainService;
import com.eduk.application.domain.ConfirmationDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ConfirmationDomainService confirmationDomainService(){
        return new ConfirmationDomainServiceImpl();
    }
}
