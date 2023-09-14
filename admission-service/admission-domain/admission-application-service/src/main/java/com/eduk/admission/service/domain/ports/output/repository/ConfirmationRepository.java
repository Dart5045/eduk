package com.eduk.admission.service.domain.ports.output.repository;

import com.eduk.admission.service.domain.entity.Confirmation;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.admission.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface ConfirmationRepository {


    Confirmation save(Confirmation confirmation);
    Optional<Confirmation> findByTrackingId(TrackingId trakingId);
    Optional<Confirmation> findById(ConfirmationId confirmationId);

}
