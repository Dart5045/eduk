package com.eduk.admission.service.domain;

import com.eduk.domain.entity.Application;
import com.eduk.domain.entity.Confirmation;
import com.eduk.domain.event.ConfirmationCreatedEvent;
import com.eduk.domain.exception.ConfirmationDomainException;
import com.eduk.admission.service.domain.ports.output.repository.ApplicationRepository;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.admission.service.domain.mapper.ConfirmationDataMapper;
import com.eduk.admission.service.domain.ports.output.message.publisher.payment.ConfirmationCreatedPaymentRequestMessagePublisher;
import com.eduk.admission.service.domain.ports.output.repository.ConfirmationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class ConfirmationCreateHelper {
    private final ConfirmationDomainService confirmationDomainService;
    private final ConfirmationRepository confirmationRepository;
    private final ApplicationRepository applicationRepository;
    private final ConfirmationDataMapper confirmationDataMapper;

    private final ConfirmationCreatedPaymentRequestMessagePublisher confirmationCreatedPaymentRequestMessagePublisher;


    public ConfirmationCreateHelper(ConfirmationDomainService confirmationDomainService
            , ConfirmationRepository confirmationRepository
            , ApplicationRepository applicationRepository
            , ConfirmationDataMapper confirmationDataMapper
            , ConfirmationCreatedPaymentRequestMessagePublisher confirmationCreatedPaymentRequestMessagePublisher) {
        this.confirmationDomainService = confirmationDomainService;
        this.confirmationRepository = confirmationRepository;
        this.applicationRepository = applicationRepository;
        this.confirmationDataMapper = confirmationDataMapper;
        this.confirmationCreatedPaymentRequestMessagePublisher = confirmationCreatedPaymentRequestMessagePublisher;
    }

    @Transactional
    public ConfirmationCreatedEvent persistConfirmation(
            CreateConfirmationCommand createConfirmationCommand){
        checkApplication(createConfirmationCommand.getApplicationId());
        Confirmation confirmation = confirmationDataMapper.createConfirmationCommandToConfirmation(createConfirmationCommand);
        ConfirmationCreatedEvent confirmationCreatedEvent = confirmationDomainService
                .validateAndInitiateConfirmation(confirmation,confirmationCreatedPaymentRequestMessagePublisher);
        saveConfirmation(confirmation);
        log.info("Confirmation is created with id:{}",confirmationCreatedEvent.getConfirmation().getId());
        return confirmationCreatedEvent;
    }


    private void checkApplication(UUID applicationID) {
        Optional<Application> application = applicationRepository.findByApplicationId(applicationID);
        if(application.isEmpty()){
            log.warn("Could not find application with application id:{}",applicationID);
            throw new ConfirmationDomainException("Could not find application with application id: "+applicationID);
        }
    }

    private Confirmation saveConfirmation(Confirmation confirmation){
        Confirmation confirmationResult = confirmationRepository.save(confirmation);
        if(confirmationResult == null){
            log.error("Could not save order");

            throw new ConfirmationDomainException("Could not save order!");
        }
        log.info("Order saved is:{}",confirmationResult.getId());
        return confirmationResult;
    }

}
