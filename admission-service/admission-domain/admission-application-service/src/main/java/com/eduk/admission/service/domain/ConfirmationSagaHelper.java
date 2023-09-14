package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.admission.service.domain.exception.ConfirmationNotFoundException;
import com.eduk.admission.service.domain.ports.output.repository.ConfirmationRepository;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.domain.valueobject.ConfirmationStatus;
import com.eduk.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ConfirmationSagaHelper {

    private final ConfirmationRepository confirmationRepository;

    public ConfirmationSagaHelper(ConfirmationRepository confirmationRepository) {
        this.confirmationRepository = confirmationRepository;
    }

    Confirmation findConfirmation(String confirmationId) {
        Optional<Confirmation> confirmationResponse = confirmationRepository.findById(new ConfirmationId(UUID.fromString(confirmationId)));
        if (confirmationResponse.isEmpty()) {
            log.error("Confirmation with id: {} could not be found!", confirmationId);
            throw new ConfirmationNotFoundException("Confirmation with id " + confirmationId + " could not be found!");
        }
        return confirmationResponse.get();
    }

    void saveConfirmation(Confirmation confirmation) {
        confirmationRepository.save(confirmation);
    }
    SagaStatus confirmationStatusToSagaStatus(ConfirmationStatus confirmationStatus) {
        switch (confirmationStatus) {
            case PAID:
                return SagaStatus.PROCESSING;
            case APPROVED:
                return SagaStatus.SUCCEEDED;
            case CANCELLING:
                return SagaStatus.COMPENSATING;
            case CANCELLED:
                return SagaStatus.COMPENSATED;
            default:
                return SagaStatus.STARTED;
        }
    }
}
