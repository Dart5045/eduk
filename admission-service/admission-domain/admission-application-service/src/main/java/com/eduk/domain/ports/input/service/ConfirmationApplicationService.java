package com.eduk.domain.ports.input.service;

import com.eduk.domain.dto.create.CreateConfirmationCommand;
import com.eduk.domain.dto.create.CreateConfirmationResponse;
import com.eduk.domain.dto.track.TrackConfirmationQuery;
import com.eduk.domain.dto.track.TrackConfirmationResponse;
import jakarta.validation.Valid;

public interface ConfirmationApplicationService {
    CreateConfirmationResponse createConfirmation(@Valid CreateConfirmationCommand createConfirmationCommand);
    TrackConfirmationResponse trackConfirmation(@Valid TrackConfirmationQuery trackConfirmationQuery);
}
