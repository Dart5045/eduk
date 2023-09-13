package com.eduk.finance.service.domain.ports.output.repository;


import com.eduk.finance.service.domain.entity.Finance;

import java.util.Optional;

public interface FinanceRepository {
    Optional<Finance> findFinanceInformation(Finance finance);
}
