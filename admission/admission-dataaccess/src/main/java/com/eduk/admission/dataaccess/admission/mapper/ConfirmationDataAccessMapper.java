package com.eduk.admission.dataaccess.admission.mapper;

import com.eduk.admission.dataaccess.admission.entity.ConfirmationEntity;
import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.valueobject.TrackingId;
import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.domain.valueobject.Money;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ConfirmationDataAccessMapper {
    public ConfirmationEntity confirmationToConfirmationEntity(Confirmation confirmation){
        return ConfirmationEntity
                .builder()
                .applicationId(confirmation.getApplicationId().getValue())
                .trackingId(confirmation.getTrackingId().getValue())
                .amount(confirmation.getAmount().getAmount())
                .confirmationStatus(confirmation.getConfirmationStatus())
                .failureMessages(confirmation.getFailureMessages()!=null?
                        String.join(",",confirmation.getFailureMessages()):"")
                .build();
    }

    public Confirmation confirmationEntityToConfirmation(ConfirmationEntity confirmationEntity)
    {
        return Confirmation
                .builder()
                .applicationId(new ApplicationId(confirmationEntity.getApplicationId()))
                .trackingId(new TrackingId(confirmationEntity.getTrackingId()))
                .confirmationStatus(confirmationEntity.getConfirmationStatus())
                .amount(new Money(confirmationEntity.getAmount()))
                .failureMessages(
                        confirmationEntity.getFailureMessages().isEmpty()? new ArrayList<>():
                                new ArrayList<>(Arrays.asList(confirmationEntity.getFailureMessages().split(",")))
                )
                .build();
    }
}
