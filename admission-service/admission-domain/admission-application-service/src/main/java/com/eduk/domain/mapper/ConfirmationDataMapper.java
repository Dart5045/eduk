package com.eduk.domain.mapper;


import com.eduk.domain.dto.message.StudentModel;
import com.eduk.domain.entity.Student;
import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.domain.valueobject.Money;
import com.eduk.domain.entity.Confirmation;
import com.eduk.domain.dto.create.CreateConfirmationCommand;
import com.eduk.domain.dto.create.CreateConfirmationResponse;
import com.eduk.domain.dto.track.TrackConfirmationResponse;
import com.eduk.domain.valueobject.StudentId;
import org.springframework.stereotype.Component;

import java.util.UUID;

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


    public Student studentModelToStudent(StudentModel studentModel) {
        return new Student(
                new StudentId(UUID.fromString(studentModel.getId())),
                studentModel.getFirstName(),
                studentModel.getLastName(),
                studentModel.getEmail()

                );
    }
}
