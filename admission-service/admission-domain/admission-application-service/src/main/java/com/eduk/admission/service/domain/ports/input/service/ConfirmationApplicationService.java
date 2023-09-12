package com.eduk.admission.service.domain.ports.input.service;

import com.eduk.admission.service.domain.dto.create.CreateConfirmationCommand;
import com.eduk.admission.service.domain.dto.create.CreateConfirmationResponse;
import com.eduk.admission.service.domain.dto.track.TrackConfirmationQuery;
import com.eduk.admission.service.domain.dto.track.TrackConfirmationResponse;
import jakarta.validation.Valid;

public interface ConfirmationApplicationService {
    CreateConfirmationResponse createConfirmation(@Valid CreateConfirmationCommand createConfirmationCommand);
    TrackConfirmationResponse trackConfirmation(@Valid TrackConfirmationQuery trackConfirmationQuery);
}
