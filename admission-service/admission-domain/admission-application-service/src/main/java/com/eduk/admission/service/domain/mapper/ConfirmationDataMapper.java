package com.eduk.admission.service.domain.mapper;


import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.domain.valueobject.Money;
import com.eduk.domain.entity.Confirmation;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.admission.service.domain.dto.track.TrackConfirmationResponse;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationDataMapper {
    public Confirmation createConfirmationCommandToConfirmation(
            CreateConfirmationCommand createConfirmationCommand){
        return Confirmation.builder()
                .amount(new Money(createConfirmationCommand.getPrice()))
                .applicationId(new ApplicationId(createConfirmationCommand.getApplicationId()))
                .build();
    }
    public CreateConfirmationResponse confirmationToCreateConfirmationResponse(
            Confirmation confirmation, String message){
        return CreateConfirmationResponse
                .builder()
                .confirmationTrackingId(confirmation.getTrackingId().getValue())
                .confirmationStatus(confirmation.getConfirmationStatus())
                .message(message)
                .build();
    }

    public TrackConfirmationResponse confirmationToTrackConfirmationResponse(Confirmation confirmation){
        return TrackConfirmationResponse
                .builder()
                .confirmationTrackingId(confirmation.getTrackingId().getValue())
                .confirmationStatus(confirmation.getConfirmationStatus())
                .failureMessages(confirmation.getFailureMessages())
                .build();
    }
}
