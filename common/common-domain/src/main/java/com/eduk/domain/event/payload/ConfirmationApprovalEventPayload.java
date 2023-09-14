package com.eduk.domain.event.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ConfirmationApprovalEventPayload {
    @JsonProperty
    private String confirmationId;
    @JsonProperty
    private String financeId;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private ZonedDateTime createdAt;
    @JsonProperty
    private String financeConfirmationStatus;
    @JsonProperty
    private List<ConfirmationApprovalEventProduct> products;
}
