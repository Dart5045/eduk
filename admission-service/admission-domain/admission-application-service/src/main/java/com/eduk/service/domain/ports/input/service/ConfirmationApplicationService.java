package com.eduk.service.domain.ports.input.service;

import com.eduk.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.service.domain.dto.track.TrackConfirmationQuery;
import com.eduk.service.domain.dto.track.TrackConfirmationResponse;
import jakarta.validation.Valid;

public interface ConfirmationApplicationService {
    CreateConfirmationResponse createConfirmation(@Valid CreateConfirmationCommand createConfirmationCommand);
    TrackConfirmationResponse trackConfirmation(@Valid TrackConfirmationQuery trackConfirmationQuery);
}
