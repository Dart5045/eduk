package com.eduk.service.domain;

import com.eduk.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.service.domain.dto.track.TrackConfirmationQuery;
import com.eduk.service.domain.dto.track.TrackConfirmationResponse;
import com.eduk.service.domain.ports.input.service.ConfirmationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Valid
@Slf4j
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationTrackCommandHandler confirmationTrackCommandHandler;
    private final ConfirmationCreateCommandHandler confirmationCreateCommandHandler;

    public ConfirmationServiceImpl(ConfirmationTrackCommandHandler confirmationTrackCommandHandler,
                                   ConfirmationCreateCommandHandler confirmationCreateCommandHandler) {
        this.confirmationTrackCommandHandler = confirmationTrackCommandHandler;
        this.confirmationCreateCommandHandler = confirmationCreateCommandHandler;
    }


    @Override
    public CreateConfirmationResponse createConfirmation(CreateConfirmationCommand createConfirmationCommand) {
        return confirmationCreateCommandHandler(createConfirmationCommand);
    }

    @Override
    public TrackConfirmationResponse trackApplicationFee(TrackConfirmationQuery trackConfirmationQuery) {
        return applicationFeeTrackCommandHandler(trackConfirmationQuery);
    }
}
