package com.eduk.service.domain.ports.input.service;

import com.eduk.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.service.domain.dto.track.TrackConfirmationQuery;
import com.eduk.service.domain.dto.track.TrackConfirmationResponse;
import jakarta.validation.Valid;

public interface ConfirmationService {
    CreateConfirmationResponse createApplicationFee(@Valid CreateConfirmationCommand createConfirmationCommand);
    TrackConfirmationResponse trackApplicationFee(@Valid TrackConfirmationQuery trackConfirmationQuery);
}
