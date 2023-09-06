package com.eduk.finance.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public FinanceDomainService financeDomainService() {
        return new FinanceDomainServiceImpl();
    }

}
