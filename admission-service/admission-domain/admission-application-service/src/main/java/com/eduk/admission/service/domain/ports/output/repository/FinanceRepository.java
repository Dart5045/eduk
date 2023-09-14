package com.eduk.admission.service.domain.ports.output.repository;

import com.eduk.admission.service.domain.entity.Finance;

import java.util.Optional;

public interface FinanceRepository {
    Optional<Finance> findFinanceInformation(Finance finance);
}
