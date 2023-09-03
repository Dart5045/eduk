package com.eduk.service.domain.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "confirmation-prefix")
public class ConfirmationServiceConfigData {
    private String paymentRequestTopicName;
    private String paymentResponseTopicName;
    private String financeApprovalConfirmationTopicName;
    private String financeApprovalResponseTopicName;
}
