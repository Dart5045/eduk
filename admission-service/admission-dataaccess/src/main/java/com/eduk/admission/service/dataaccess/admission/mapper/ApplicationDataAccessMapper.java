package com.eduk.admission.service.dataaccess.admission.mapper;

import com.eduk.admission.service.dataaccess.admission.entity.ApplicationEntity;
import com.eduk.domain.entity.Application;
import com.eduk.domain.valueobject.ApplicationId;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDataAccessMapper {

    public Application applicationEntityToApplication(ApplicationEntity applicationEntity) {
        return Application
                .builder()
                .applicationId(new ApplicationId(applicationEntity.getId()))
                .build();
    }
}
