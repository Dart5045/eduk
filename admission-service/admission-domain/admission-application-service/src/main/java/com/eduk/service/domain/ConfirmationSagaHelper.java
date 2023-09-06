package com.eduk.service.domain;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.exception.ConfirmationNotFoundException;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.service.domain.ports.output.repository.ConfirmationRepository;
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
}
