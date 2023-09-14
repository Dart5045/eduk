package com.eduk.admission.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateConfirmationCommand {
    @NotNull
    private  UUID applicationId;
    @NotNull
    private  UUID termRequested;

    @NotNull
    private  UUID financeId;
    @NotNull
    private  BigDecimal price;
}
