package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.outbox.scheduler.payment.PaymentOutboxHelper;
import com.eduk.admission.service.domain.event.ConfirmationCreatedEvent;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.admission.service.domain.mapper.ConfirmationDataMapper;
import com.eduk.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ConfirmationCreateCommandHandler {

    private final ConfirmationCreateHelper confirmationCreateHelper;
    private final ConfirmationDataMapper confirmationDataMapper;

    private final PaymentOutboxHelper paymentOutboxHelper;
    private final ConfirmationSagaHelper confirmationSagaHelper;

    public ConfirmationCreateCommandHandler(ConfirmationCreateHelper confirmationCreateHelper
            , ConfirmationDataMapper confirmationDataMapper
            , PaymentOutboxHelper paymentOutboxHelper, ConfirmationSagaHelper confirmationSagaHelper) {
        this.confirmationCreateHelper = confirmationCreateHelper;
        this.confirmationDataMapper = confirmationDataMapper;
        this.paymentOutboxHelper = paymentOutboxHelper;
        this.confirmationSagaHelper = confirmationSagaHelper;
    }


    public CreateConfirmationResponse createConfirmationResponse(
            CreateConfirmationCommand createConfirmationCommand){
        ConfirmationCreatedEvent confirmationCreatedEvent = confirmationCreateHelper.persistConfirmation(createConfirmationCommand);
        log.info("Confirmation is created with id:{}",confirmationCreatedEvent.getConfirmation().getId());

        CreateConfirmationResponse confirmationResponse = confirmationDataMapper
                .confirmationToCreateConfirmationResponse(confirmationCreatedEvent.getConfirmation(),
                        "Confirmation created successfully");
        paymentOutboxHelper.savePaymentOutboxMessage(
                confirmationDataMapper.confirmationCreatedEventToConfirmationPaymentEventPayload(confirmationCreatedEvent)
                ,confirmationCreatedEvent.getConfirmation().getConfirmationStatus()
                ,confirmationSagaHelper.confirmationStatusToSagaStatus(confirmationCreatedEvent.getConfirmation().getConfirmationStatus())
                ,OutboxStatus.STARTED
                ,UUID.randomUUID());
        log.info("Returning CreateConfirmationResponse with confirmation id: {}",confirmationCreatedEvent.getConfirmation().getId());

        return confirmationDataMapper.confirmationToCreateConfirmationResponse(confirmationCreatedEvent.getConfirmation(),"Order Created Successfully");
    }

}
