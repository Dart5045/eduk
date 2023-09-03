package com.eduk.service.domain.dto.track;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class TrackConfirmationQuery {
    @NotNull
    private final UUID confirmationTrackingId;
}
