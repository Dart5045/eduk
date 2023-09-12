package com.eduk.domain;

import com.eduk.domain.event.ConfirmationCreatedEvent;
import com.eduk.domain.ports.output.message.publisher.payment.ConfirmationCreatedPaymentRequestMessagePublisher;
import com.eduk.domain.dto.create.CreateConfirmationCommand;
import com.eduk.domain.dto.create.CreateConfirmationResponse;
import com.eduk.domain.mapper.ConfirmationDataMapper;
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
