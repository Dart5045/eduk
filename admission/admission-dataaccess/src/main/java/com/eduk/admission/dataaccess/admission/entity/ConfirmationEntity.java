package com.eduk.admission.dataaccess.admission.entity;


import com.eduk.admission.dataaccess.admission.entity.ApplicationEntity;
import com.eduk.application.domain.valueobject.ConfirmationStatus;
import com.eduk.domain.valueobject.ApplicationId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
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
