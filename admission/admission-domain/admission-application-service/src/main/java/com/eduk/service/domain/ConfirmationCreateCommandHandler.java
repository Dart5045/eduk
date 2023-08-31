package com.eduk.service.domain;

import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.service.domain.mapper.ConfirmationDataMapper;
import com.eduk.service.domain.ports.output.message.publisher.payment.ConfirmationCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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


    public CreateConfirmationResponse createApplicationFeeResponse(
            CreateConfirmationCommand createConfirmationCommand){
        ConfirmationCreatedEvent confirmationCreatedEvent = confirmationCreateHelper.persistConfirmation(createConfirmationCommand);
        log.info("Confirmation is created with id:{}",confirmationCreatedEvent.getConfirmation().getId());
        confirmationCreatedPaymentRequestMessagePublisher.publish(confirmationCreatedEvent);
        return confirmationDataMapper.confirmationToCreateConfirmationResponse(confirmationCreatedEvent.getConfirmation(),"Order Created Successfully");
    }

}
