package com.eduk.service.domain;

import com.eduk.application.domain.ConfirmationDomainService;
import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.event.ConfirmationCreatedEvent;
import com.eduk.application.domain.exception.ApplicationDomainException;
import com.eduk.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.service.domain.mapper.ConfirmationDataMapper;
import com.eduk.service.domain.ports.output.repository.ApplicationRepository;
import com.eduk.service.domain.ports.output.repository.ConfirmationRepository;
import com.eduk.service.domain.ports.output.repository.FinanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class ConfirmationCreateCommandHandler {
    private final ConfirmationDomainService confirmationDomainService;
    private final ConfirmationRepository confirmationRepository;
    private final ApplicationRepository applicationRepository;
    private final FinanceRepository financeRepository;
    private final ConfirmationDataMapper confirmationDataMapper;

    public ConfirmationCreateCommandHandler(ConfirmationDomainService confirmationDomainService,
                                            ConfirmationRepository confirmationRepository,
                                            ApplicationRepository applicationRepository,
                                            FinanceRepository financeRepository,
                                            ConfirmationDataMapper confirmationDataMapper) {
        this.confirmationDomainService = confirmationDomainService;
        this.confirmationRepository = confirmationRepository;
        this.applicationRepository = applicationRepository;
        this.financeRepository = financeRepository;
        this.confirmationDataMapper = confirmationDataMapper;
    }

    @Transactional
    public CreateConfirmationResponse createApplicationFeeResponse(
            CreateConfirmationCommand createConfirmationCommand){
        checkApplication(createConfirmationCommand.getApplicationId());
        Confirmation confirmation = confirmationDataMapper.createConfirmationCommandToConfirmation(createConfirmationCommand);
        ConfirmationCreatedEvent confirmationCreatedEvent = confirmationDomainService.validateAndInitiatePaymentFee(confirmation);
        Confirmation confirmationResult = saveConfirmation(confirmation);
        log.info("Confirmation is created with id:{}",confirmationResult.getId());
        return confirmationDataMapper.confirmationToCreateConfirmationResponse(confirmationResult);
    }

    private void checkApplication(UUID applicationID) {
        Optional<Application> application = applicationRepository.findByApplicationId(applicationID);
        if(application.isEmpty()){
            log.warn("Could not find application with application id:{}",applicationID);
            throw new ApplicationDomainException("Could not find application with application id: "+applicationID);
        }
    }

    private Confirmation saveConfirmation(Confirmation confirmation){
        Confirmation confirmationResult = confirmationRepository.save(confirmation);
        if(confirmationResult == null){
            log.error("Could not save order");

            throw new ApplicationDomainException("Could not save order!");
        }
        log.info("Order saved is:{}",confirmationResult.getId());
        return confirmationResult;
    }

}
