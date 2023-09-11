package com.eduk.admission.service.domain;

import com.eduk.domain.event.ConfirmationCreatedEvent;
import com.eduk.admission.service.domain.ports.output.message.publisher.payment.ConfirmationCreatedPaymentRequestMessagePublisher;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.admission.service.domain.mapper.ConfirmationDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfirmationCreateCommandHandler {

    private final ConfirmationCreateHelper confirmationCreateHelper;
    private final ConfirmationDataMapper confirmationDataMapper;
    private final ConfirmationCreatedPaymentRequestMessagePublisher confirmationCreatedPaymentRequestMessagePublisher;

    public ConfirmationCreateCommandHandler(ConfirmationCreateHelper confirmationCreateHelper
            , ConfirmationDataMapper confirmationDataMapper
            , ConfirmationCreatedPaymentRequestMessagePublisher confirmationCreatedPaymentRequestMessagePublisher) {
        this.confirmationCreateHelper = confirmationCreateHelper;
        this.confirmationDataMapper = confirmationDataMapper;
        this.confirmationCreatedPaymentRequestMessagePublisher = confirmationCreatedPaymentRequestMessagePublisher;
    }


    public CreateConfirmationResponse createConfirmationResponse(
            CreateConfirmationCommand createConfirmationCommand){
        ConfirmationCreatedEvent confirmationCreatedEvent = confirmationCreateHelper.persistConfirmation(createConfirmationCommand);
        log.info("Confirmation is created with id:{}",confirmationCreatedEvent.getConfirmation().getId());
        confirmationCreatedPaymentRequestMessagePublisher.publish(confirmationCreatedEvent);
        return confirmationDataMapper.confirmationToCreateConfirmationResponse(confirmationCreatedEvent.getConfirmation(),"Order Created Successfully");
    }

}
