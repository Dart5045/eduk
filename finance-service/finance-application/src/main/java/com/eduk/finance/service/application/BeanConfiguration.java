package com.eduk.finance.service.application;

import com.eduk.finance.service.domain.FinanceDomainService;
import com.eduk.finance.service.domain.FinanceDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public FinanceDomainService restaurantDomainService() {

        return new FinanceDomainServiceImpl();
    }

}
