package com.eduk.admission.service.domain.mapper;


import com.eduk.admission.service.domain.entity.*;
import com.eduk.admission.service.domain.event.ConfirmationCancelledEvent;
import com.eduk.admission.service.domain.event.ConfirmationCreatedEvent;
import com.eduk.admission.service.domain.event.ConfirmationPaidEvent;
import com.eduk.domain.event.payload.ConfirmationApprovalEventPayload;
import com.eduk.domain.event.payload.ConfirmationApprovalEventProduct;
import com.eduk.domain.event.payload.ConfirmationPaymentEventPayload;
import com.eduk.domain.valueobject.*;
import com.eduk.admission.service.domain.dto.message.StudentModel;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.admission.service.domain.dto.track.TrackConfirmationResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConfirmationDataMapper {
    public Finance createConfirmationCommandToFinance(CreateConfirmationCommand createConfirmationCommand) {
        return Finance.builder()
                .financeId(new FinanceId(createConfirmationCommand.getFinanceId()))
                /*.products(createConfirmationCommand.getItems().stream().map(confirmationItem ->
                                new Product(new ProductId(confirmationItem.getProductId())))
                        .collect(Collectors.toList()))*/
                .build();
    }

    public Confirmation createConfirmationCommandToConfirmation(CreateConfirmationCommand createConfirmationCommand) {
        return Confirmation.builder()
                .applicationId(new ApplicationId(createConfirmationCommand.getApplicationId()))
                //.financeId(new FinanceId(createConfirmationCommand.getFinanceId()))
                //.deliveryAddress(confirmationAddressToStreetAddress(createConfirmationCommand.getAddress()))
                .amount(new Money(createConfirmationCommand.getPrice()))
                //.items(confirmationItemsToConfirmationItemEntities(createConfirmationCommand.getItems()))
                .build();
    }

    public CreateConfirmationResponse confirmationToCreateConfirmationResponse(Confirmation confirmation, String message) {
        return CreateConfirmationResponse.builder()
                .confirmationTrackingId(confirmation.getTrackingId().getValue())
                .confirmationStatus(confirmation.getConfirmationStatus())
                .message(message)
                .build();
    }

    public TrackConfirmationResponse confirmationToTrackConfirmationResponse(Confirmation confirmation) {
        return TrackConfirmationResponse.builder()
                .confirmationTrackingId(confirmation.getTrackingId().getValue())
                .confirmationStatus(confirmation.getConfirmationStatus())
                .failureMessages(confirmation.getFailureMessages())
                .build();
    }

    public ConfirmationPaymentEventPayload confirmationCreatedEventToConfirmationPaymentEventPayload(ConfirmationCreatedEvent confirmationCreatedEvent) {
        return ConfirmationPaymentEventPayload.builder()
                .applicationId(confirmationCreatedEvent.getConfirmation().getApplicationId().getValue().toString())
                .confirmationId(confirmationCreatedEvent.getConfirmation().getId().getValue().toString())
                .price(confirmationCreatedEvent.getConfirmation().getAmount().getAmount())
                .createdAt(confirmationCreatedEvent.getCreatedAt())
                .paymentConfirmationStatus(PaymentConfirmationStatus.PENDING.name())
                .build();
    }

    public ConfirmationPaymentEventPayload confirmationCancelledEventToConfirmationPaymentEventPayload(ConfirmationCancelledEvent
                                                                                          confirmationCancelledEvent) {
        return ConfirmationPaymentEventPayload.builder()
                .applicationId(confirmationCancelledEvent.getConfirmation().getApplicationId().getValue().toString())
                .confirmationId(confirmationCancelledEvent.getConfirmation().getId().getValue().toString())
                .price(confirmationCancelledEvent.getConfirmation().getAmount().getAmount())
                .createdAt(confirmationCancelledEvent.getCreatedAt())
                .paymentConfirmationStatus(PaymentConfirmationStatus.CANCELLED.name())
                .build();
    }

    public ConfirmationApprovalEventPayload confirmationPaidEventToConfirmationApprovalEventPayload(ConfirmationPaidEvent confirmationPaidEvent) {
        return ConfirmationApprovalEventPayload.builder()
                .confirmationId(confirmationPaidEvent.getConfirmation().getId().getValue().toString())
                //.financeId(confirmationPaidEvent.getConfirmation().getFinanceId().getValue().toString())
                .financeConfirmationStatus(FinanceConfirmationStatus.PAID.name())
//                .products(confirmationPaidEvent.getConfirmation().getItems().stream().map(confirmationItem ->
//                        ConfirmationApprovalEventProduct.builder()
//                                .id(confirmationItem.getProduct().getId().getValue().toString())
//                                .quantity(confirmationItem.getQuantity())
//                                .build()).collect(Collectors.toList()))
                .price(confirmationPaidEvent.getConfirmation().getAmount().getAmount())
                .createdAt(confirmationPaidEvent.getCreatedAt())
                .build();
    }


    public Student studentModelToStudent(StudentModel studentModel) {
        return new Student(new StudentId(UUID.fromString(studentModel.getId())),
                studentModel.getFirstName(),
                studentModel.getLastName(),
                studentModel.getEmail());
    }
/*
    private List<ConfirmationItem> confirmationItemsToConfirmationItemEntities(
            List<com.food.confirmationing.system.confirmation.service.domain.dto.create.ConfirmationItem> confirmationItems) {
        return confirmationItems.stream()
                .map(confirmationItem ->
                        ConfirmationItem.builder()
                                .product(new Product(new ProductId(confirmationItem.getProductId())))
                                .price(new Money(confirmationItem.getPrice()))
                                .quantity(confirmationItem.getQuantity())
                                .subTotal(new Money(confirmationItem.getSubTotal()))
                                .build()).collect(Collectors.toList());
    }
*/
//    private StreetAddress confirmationAddressToStreetAddress(ConfirmationAddress confirmationAddress) {
//        return new StreetAddress(
//                UUID.randomUUID(),
//                confirmationAddress.getStreet(),
//                confirmationAddress.getPostalCode(),
//                confirmationAddress.getCity()
//        );
//}
}
