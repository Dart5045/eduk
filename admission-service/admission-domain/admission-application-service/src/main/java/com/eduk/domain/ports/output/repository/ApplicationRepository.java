package com.eduk.domain.ports.output.repository;

import com.eduk.domain.entity.Application;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository {
    Application save(Application application);
    Optional<Application> findByApplicationId(UUID applicationId);
}
