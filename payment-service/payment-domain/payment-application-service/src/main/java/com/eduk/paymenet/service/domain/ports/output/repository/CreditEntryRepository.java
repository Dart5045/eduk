package com.eduk.paymenet.service.domain.ports.output.repository;

import com.eduk.admission.service.domain.valueobject.ApplicationId;
import com.eduk.payment.service.domain.entity.CreditEntry;

import java.util.Optional;

public interface CreditEntryRepository {
    CreditEntry save(CreditEntry creditEntry);
    Optional<CreditEntry> findByApplicationId(ApplicationId applicationId);
}
