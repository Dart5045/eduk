package com.eduk.service.domain.dto.create;

import com.eduk.application.domain.valueobject.ConfirmationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class CreateConfirmationResponse {
    @NotNull
    private final UUID confirmationTrackingId;
    @NotNull
    private final ConfirmationStatus confirmationStatus;
    @NotNull
    private final String message;
}
