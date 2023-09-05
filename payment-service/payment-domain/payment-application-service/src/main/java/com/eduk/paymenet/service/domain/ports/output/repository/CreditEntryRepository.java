package com.eduk.paymenet.service.domain.ports.output.repository;

import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.payment.service.domain.entity.CreditEntry;

import java.util.Optional;
import java.util.UUID;

public interface CreditEntryRepository {
    CreditEntry save(CreditEntry creditEntry);
    Optional<CreditEntry> findByApplicationId(ApplicationId applicationId);
}
