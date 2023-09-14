package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.entity.Application;
import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.entity.Finance;
import com.eduk.admission.service.domain.event.ConfirmationCreatedEvent;
import com.eduk.admission.service.domain.exception.ConfirmationDomainException;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.admission.service.domain.mapper.ConfirmationDataMapper;
import com.eduk.admission.service.domain.ports.output.repository.ApplicationRepository;
import com.eduk.admission.service.domain.ports.output.repository.ConfirmationRepository;
import com.eduk.admission.service.domain.ports.output.repository.FinanceRepository;
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

    private final FinanceRepository financeRepository;

    private final ConfirmationDataMapper confirmationDataMapper;

    public ConfirmationCreateHelper(ConfirmationDomainService confirmationDomainService,
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
    public ConfirmationCreatedEvent persistConfirmation(CreateConfirmationCommand createConfirmationCommand) {
        checkApplication(createConfirmationCommand.getApplicationId());
        Finance finance = checkFinance(createConfirmationCommand);
        Confirmation confirmation = confirmationDataMapper.createConfirmationCommandToConfirmation(createConfirmationCommand);
        ConfirmationCreatedEvent confirmationCreatedEvent = confirmationDomainService.validateAndInitiateConfirmation(confirmation, finance);
        saveConfirmation(confirmation);
        log.info("Confirmation is created with id: {}", confirmationCreatedEvent.getConfirmation().getId().getValue());
        return confirmationCreatedEvent;
    }

    private Finance checkFinance(CreateConfirmationCommand createConfirmationCommand) {
        Finance finance = confirmationDataMapper.createConfirmationCommandToFinance(createConfirmationCommand);
        Optional<Finance> optionalFinance = financeRepository.findFinanceInformation(finance);
        if (optionalFinance.isEmpty()) {
            log.warn("Could not find finance with finance id: {}", createConfirmationCommand.getFinanceId());
            throw new ConfirmationDomainException("Could not find finance with finance id: " +
                    createConfirmationCommand.getFinanceId());
        }
        return optionalFinance.get();
    }

    private void checkApplication(UUID applicationId) {
        Optional<Application> application = applicationRepository.findByApplicationId(applicationId);
        if (application.isEmpty()) {
            log.warn("Could not find application with application id: {}", applicationId);
            throw new ConfirmationDomainException("Could not find application with application id: " + applicationId);
        }
    }

    private Confirmation saveConfirmation(Confirmation confirmation) {
        Confirmation confirmationResult = confirmationRepository.save(confirmation);
        if (confirmationResult == null) {
            log.error("Could not save confirmation!");
            throw new ConfirmationDomainException("Could not save confirmation!");
        }
        log.info("Confirmation is saved with id: {}", confirmationResult.getId().getValue());
        return confirmationResult;
    }
}
