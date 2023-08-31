package com.eduk.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class CreateConfirmationCommand {
    @NotNull
    private final UUID applicationId;
    @NotNull
    private final UUID termRequested;
    @NotNull
    private final BigDecimal price;
}
