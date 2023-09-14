package com.eduk.admission.service.dataaccess.admission.adapter;

import com.eduk.admission.service.dataaccess.admission.mapper.ApplicationDataAccessMapper;
import com.eduk.admission.service.dataaccess.admission.repository.ApplicationJpaRepository;
import com.eduk.admission.service.domain.entity.Application;
import com.eduk.admission.service.domain.ports.output.repository.ApplicationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ApplicationRepositoryImpl implements ApplicationRepository {

    private final ApplicationJpaRepository applicationJpaRepository;
    private final ApplicationDataAccessMapper applicationDataAccessMapper;

    public ApplicationRepositoryImpl(ApplicationJpaRepository applicationJpaRepository,
                                     ApplicationDataAccessMapper applicationDataAccessMapper) {
        this.applicationJpaRepository = applicationJpaRepository;
        this.applicationDataAccessMapper = applicationDataAccessMapper;
    }

    @Override
    public Application save(Application application) {
        return null;
    }

    @Override
    public Optional<Application> findByApplicationId(UUID applicationId) {
        return applicationJpaRepository
                .findById(applicationId)
                .map(applicationDataAccessMapper::applicationEntityToApplication);
    }
}
