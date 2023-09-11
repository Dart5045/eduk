package com.eduk.student.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "student-prefix")
public class StudentServiceConfigData {
    private String studentTopicName;
}
