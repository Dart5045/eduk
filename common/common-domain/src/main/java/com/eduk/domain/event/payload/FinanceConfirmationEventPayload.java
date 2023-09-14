package com.eduk.domain.event.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FinanceConfirmationEventPayload {

    @JsonProperty
    private String confirmationId;

    @JsonProperty
    private String financeId;

    @JsonProperty
    private ZonedDateTime createdAt;

    @JsonProperty
    private String confirmationApprovalStatus;

    @JsonProperty
    private List<String> failureMessages;


}
