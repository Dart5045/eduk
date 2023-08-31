package com.eduk.service.domain.dto.track;

import com.eduk.domain.valueobject.PaymentStatus;
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
    private final UUID applicationFeeTrackingId;
    @NotNull
    private final PaymentStatus paymentStatus;
    private final List<String> failureMessages;
}
