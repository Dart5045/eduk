package com.eduk.admission.service.dataaccess.admission.entity;


import com.eduk.admission.service.domain.valueobject.ConfirmationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "confirmations")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationEntity {

    @Id
    private UUID id;
    private UUID applicationId;
    private UUID trackingId;
    private BigDecimal amount;
    private ConfirmationStatus confirmationStatus;
    private String failureMessages;
}
