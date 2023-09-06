package com.eduk.finance.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "finance-service")
public class FinanceServiceConfigData {
    private String financeApprovalRequestTopicName;
    private String financeApprovalResponseTopicName;
}
