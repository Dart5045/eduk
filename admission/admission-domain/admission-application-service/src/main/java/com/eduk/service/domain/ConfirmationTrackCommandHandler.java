package com.eduk.service.domain;

import com.eduk.service.domain.dto.track.TrackConfirmationQuery;
import com.eduk.service.domain.dto.track.TrackConfirmationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfirmationTrackCommandHandler {
    public TrackConfirmationResponse applicationFeeTrackCommandHandler(
            TrackConfirmationQuery trackConfirmationQuery);{

    }


}
