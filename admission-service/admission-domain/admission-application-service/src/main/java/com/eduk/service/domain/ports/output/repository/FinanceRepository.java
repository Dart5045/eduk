package com.eduk.service.domain.ports.output.repository;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.valueobject.TrackingId;

import java.util.Optional;

public interface FinanceRepository {
    void findFinanceInformation();
}
