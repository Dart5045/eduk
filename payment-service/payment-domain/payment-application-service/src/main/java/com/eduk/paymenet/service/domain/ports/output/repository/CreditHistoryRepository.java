package com.eduk.paymenet.service.domain.ports.output.repository;

import com.eduk.payment.service.domain.entity.CreditHistory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreditHistoryRepository {
    CreditHistory save(CreditHistory creditHistory) ;
    Optional<List<CreditHistory>> findByApplicationID(UUID applicationId);
}
