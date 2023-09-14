package com.eduk.domain.event.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ConfirmationPaymentEventPayload {

    @JsonProperty
    private String id;
    @JsonProperty
    private String sagaId;
    @JsonProperty
    private String confirmationId;
    @JsonProperty
    private String applicationId;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private ZonedDateTime createdAt;
    @JsonProperty
    private String paymentConfirmationStatus;
}
