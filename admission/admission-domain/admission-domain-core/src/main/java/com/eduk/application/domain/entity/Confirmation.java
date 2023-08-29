package com.eduk.application.domain.entity;

import com.eduk.application.domain.valueobject.ConfirmationId;
import com.eduk.domain.entity.BaseEntity;
import com.eduk.domain.valueobject.ApplicationId;

import java.util.UUID;

public class Confirmation extends BaseEntity<ConfirmationId> {
    protected  final ApplicationId applicationId;


    public void initializeConfirmation(){
        this.setId(new ConfirmationId(UUID.randomUUID()));
    }
}
