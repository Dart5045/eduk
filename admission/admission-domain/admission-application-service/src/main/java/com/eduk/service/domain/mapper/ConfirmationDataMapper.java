package com.eduk.service.domain.mapper;


import com.eduk.application.domain.entity.Confirmation;
import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.domain.valueobject.Money;
import com.eduk.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.service.domain.dto.create.CreateConfirmationResponse;
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
            Confirmation confirmation){
        return CreateConfirmationResponse
                .builder()
                .confirmationStatus(confirmation.getConfirmationStatus())
                .build();
    }
}
