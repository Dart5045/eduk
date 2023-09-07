package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.admission.service.domain.dto.track.TrackConfirmationQuery;
import com.eduk.admission.service.domain.dto.track.TrackConfirmationResponse;
import com.eduk.admission.service.domain.ports.input.service.ConfirmationApplicationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Valid
@Slf4j
public class ConfirmationApplicationServiceImpl implements ConfirmationApplicationService {

    private final ConfirmationTrackCommandHandler confirmationTrackCommandHandler;
    private final ConfirmationCreateCommandHandler confirmationCreateCommandHandler;

    public ConfirmationApplicationServiceImpl(ConfirmationTrackCommandHandler confirmationTrackCommandHandler,
                                              ConfirmationCreateCommandHandler confirmationCreateCommandHandler) {
        this.confirmationTrackCommandHandler = confirmationTrackCommandHandler;
        this.confirmationCreateCommandHandler = confirmationCreateCommandHandler;
    }


    @Override
    public CreateConfirmationResponse createConfirmation(CreateConfirmationCommand createConfirmationCommand) {
        return confirmationCreateCommandHandler.createConfirmationResponse(createConfirmationCommand);
    }

    @Override
    public TrackConfirmationResponse trackConfirmation(TrackConfirmationQuery trackConfirmationQuery) {
        return confirmationTrackCommandHandler.trackConfirmation(trackConfirmationQuery);
    }
}
