package com.eduk.service.domain.ports.output.repository;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.valueobject.TrackingId;

import java.util.Optional;

public interface ConfirmationRepository {
    Confirmation save(Confirmation confirmation);
    Optional<Confirmation> findByTrackingId(TrackingId trakingId);
}
