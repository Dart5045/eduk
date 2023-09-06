package com.eduk.admission.application.service.dataaccess.admission.adapter;

import com.eduk.admission.application.service.dataaccess.admission.entity.ConfirmationEntity;
import com.eduk.admission.application.service.dataaccess.admission.mapper.ConfirmationDataAccessMapper;
import com.eduk.admission.application.service.dataaccess.admission.repository.ConfirmationJpaRepository;
import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.valueobject.TrackingId;
import com.eduk.domain.valueobject.ConfirmationId;
import com.eduk.service.domain.ports.output.repository.ConfirmationRepository;

import java.util.Optional;
import java.util.UUID;

public class ConfirmationRepositoryImpl implements ConfirmationRepository {
    private final ConfirmationJpaRepository confirmationJpaRepository;
    private final ConfirmationDataAccessMapper confirmationDataAccessMapper;

    public ConfirmationRepositoryImpl(ConfirmationJpaRepository confirmationJpaRepository, ConfirmationDataAccessMapper confirmationDataAccessMapper) {
        this.confirmationJpaRepository = confirmationJpaRepository;
        this.confirmationDataAccessMapper = confirmationDataAccessMapper;
    }

    @Override
    public Confirmation save(Confirmation confirmation) {
        ConfirmationEntity confirmationEntity = confirmationDataAccessMapper.confirmationToConfirmationEntity(confirmation);
        return confirmationDataAccessMapper.confirmationEntityToConfirmation(confirmationJpaRepository.save(confirmationEntity));
    }

    @Override
    public Optional<Confirmation> findByTrackingId(TrackingId trackingId) {
        return confirmationJpaRepository.findByTrackingId(trackingId.getValue()).map(
                confirmationDataAccessMapper::confirmationEntityToConfirmation
        );
    }

    @Override
    public Optional<Confirmation> findById(ConfirmationId confirmationId) {
        return confirmationJpaRepository.findById(confirmationId.getValue())
                .map(confirmationDataAccessMapper::confirmationEntityToConfirmation);
    }
}
