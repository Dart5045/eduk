package com.eduk.domain.dto.track;

import com.eduk.domain.valueobject.ConfirmationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class TrackConfirmationResponse {
    @NotNull
    private final UUID confirmationTrackingId;
    @NotNull
    private final ConfirmationStatus confirmationStatus;
    private final List<String> failureMessages;
}
