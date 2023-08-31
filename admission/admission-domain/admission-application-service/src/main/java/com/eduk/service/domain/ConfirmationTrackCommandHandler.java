package com.eduk.service.domain;

import com.eduk.application.domain.entity.Confirmation;
import com.eduk.application.domain.exception.ConfirmationNotFoundException;
import com.eduk.application.domain.valueobject.TrackingId;
import com.eduk.service.domain.dto.track.TrackConfirmationQuery;
import com.eduk.service.domain.dto.track.TrackConfirmationResponse;
import com.eduk.service.domain.mapper.ConfirmationDataMapper;
import com.eduk.service.domain.ports.output.repository.ConfirmationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
public class ConfirmationTrackCommandHandler {
    private final ConfirmationDataMapper confirmationDataMapper;
    private final ConfirmationRepository confirmationRepository;


    public ConfirmationTrackCommandHandler(ConfirmationDataMapper confirmationDataMapper
            , ConfirmationRepository confirmationRepository) {
        this.confirmationDataMapper = confirmationDataMapper;
        this.confirmationRepository = confirmationRepository;
    }

    @Transactional(readOnly = true)
    public TrackConfirmationResponse trackConfirmation(TrackConfirmationQuery trackConfirmationQuery){
        Optional<Confirmation> confirmationResult = confirmationRepository.findByTrackingId(new TrackingId(trackConfirmationQuery.getConfirmationTrackingId()));
        if(confirmationResult.isEmpty()){
            log.warn("Could not find confirmation with track id:{}",trackConfirmationQuery.getConfirmationTrackingId());
            throw new ConfirmationNotFoundException("Could not find confirmation with track id:"+trackConfirmationQuery.getConfirmationTrackingId());
        }

        return confirmationDataMapper.confirmationToTrackConfirmationResponse(confirmationResult.get());
    }
}
